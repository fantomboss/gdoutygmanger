package com.wteam.backmanage.Web;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.wteam.backmanage.Entity.Shiro.User;
import com.wteam.backmanage.jpaRepository.ShiroRepository.UserRepository;
import com.wteam.backmanage.vo.ResultVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.util.Date;

/**
 * @author fantomboss
 * @date 2019/4/20-17:07es
 */
@RestController
@Transactional
@RequestMapping("/user")
public class userController {

  @Autowired
  private UserRepository userR;

  /**
   * 登录
   * @param account
   * @param psw
   * @param cpuid
   * @return
   */
  @PostMapping("/login")
  public ResultVO checkByAccount(
          @RequestParam String account,
          @RequestParam String psw,
          @RequestParam String cpuid){
     User uu = userR.findByName(account);
     if(uu!=null&&uu.getPsw().equals(psw)) {
       if(uu.getCpuId()!=null){
         //检查授权是否过期
         if(uu.getCpuId().equals(cpuid)&&checkCpuTime(uu))
           return ResultVO.ok("登录成功");
         else
           return ResultVO.erro("使用失效过期");
       } else{
         //第一次登录加入授权
         uu.setCpuId(cpuid);
         //初始化使用时间
         uu = setTime(uu);
         userR.save(uu);
         return ResultVO.ok("登录成功");
       }
     }
     else
       return ResultVO.erro("禁止登录");
  }

  /**
   * 管理员登录
   * @param account
   * @param psw
   * @return
   */
  @RequestMapping("/superLogin")
  public ResultVO superLogin(
          @RequestParam String account,
          @RequestParam String psw) {
    /**
     * 使用Shiro认证
     */
    //1、获取Subject
    Subject subject = SecurityUtils.getSubject();
    //2、封装用户数据
    UsernamePasswordToken token = new UsernamePasswordToken(account,psw);
    //3、执行登录操作
    try {
      subject.login(token);
      System.out.println("登录成功");
      //登录成功
      return ResultVO.ok("登录成功");
    }catch (UnknownAccountException e1){
      //账号不存在
      return ResultVO.erro("账号不存在");
    }catch (IncorrectCredentialsException e2){
      //密码错误
      return ResultVO.erro("密码错误");
    }
  }


  /**
   * 注册
   * @return
   */
  @PostMapping("/register")
  public ResultVO signIn(
          @RequestParam String account,
          @RequestParam String psw,
          @RequestParam Long efTime){
    if(userR.findByName(account)==null) {
      User uu = new User();
      uu.setName(account);
      uu.setPsw(psw);
      uu.setEffectTime(efTime);
      userR.save(uu);
      return ResultVO.ok("注册成功");
    }
    else
      return ResultVO.erro("账号已存在");
  }

  /**
   * 获取用户列表
   * @return
   */
  @GetMapping("/userPage")
  public ResultVO<Page<User>> getUserList(
          @RequestParam String account,
          @RequestParam(required = false) Integer page,
          @RequestParam(required = false) Integer size){
    if(page==null) page = 1;
    if(size==null) size = 8;
    Pageable pageable = PageRequest.of(page-1,size);
    Page<User> pu = userR.findByNameLike("%"+account+"%",pageable);
    return new ResultVO<Page<User>>().ok(pu);
  }

  /**
   * 更改用户
   * @return
   */
  @PostMapping("/changeUser")
  public ResultVO changeUser(
          @RequestParam String account,
          @RequestParam String psw,
          @RequestParam long eft){
    User uu = userR.findByName(account);
    if(uu != null) {
      uu.setPsw(psw);
      uu.setEffectTime(eft);
      //判断用户的是否已经启用过软件，设置新的失效时间
      String startTime = uu.getStartTime();
      if(startTime != null) {
        if(!startTime.equals("")) {
          Date date = DateUtil.parse(startTime);
          Date Time = DateUtil.offsetDay(date, (int) eft);
          String endTime = DateUtil.format(Time, "yyyy-MM-dd");
          uu.setEndTime(endTime);
        }
      }
      //save
      userR.save(uu);
      return ResultVO.ok("更改用户");
    }else {
      return ResultVO.erro("用户不存在");
    }
  }

  /**
   * 删除用户
   * @return
   */
  @GetMapping("/deleteUser")
  public ResultVO deleteUser(
          @RequestParam int userId){
    User uu = userR.findByUserId(userId);
    if(uu!=null) {
      userR.delete(uu);
      return ResultVO.ok("删除成功");
    } else
      return ResultVO.erro("用户不存在");
  }

  /**
   * 用户更改密码
   * @return
   */
  @PostMapping("/changeUserPsw")
  public ResultVO changePassword(
          @RequestParam String account,
          @RequestParam String oldPsw,
          @RequestParam String newPsw){
    User uu = userR.findByName(account);
    if(uu!=null&&uu.getPsw().equals(oldPsw)) {
      uu.setPsw(newPsw);
      return ResultVO.ok("修改成功");
    } else
      return ResultVO.erro("用户不存在或密码错误");
  }

  //检查授权是否过期
  public Boolean checkCpuTime(User user){
    //获取当前用户有效时常
    long et = user.getEffectTime();
    //获取当前用户开始使用时间
    String s = user.getStartTime();
    Date date = DateUtil.parse(s,"yyyy-MM-dd");
    //获取当前日期
    Date date1= DateUtil.date();
    DateFormat df = DateFormat.getDateInstance();
    df.format(date1);
    //对比时间
    long betweenDay = DateUtil.between(date, date, DateUnit.DAY);
    if(et>=betweenDay)
      return true;
    else
      return false;
  }

  //设置初始化时间
  public User setTime(User uu){
    Date nawTime = DateUtil.date();
    String nawTimeF = DateUtil.format(nawTime, "yyyy-MM-dd");
    Date endTime = DateUtil.offsetDay(nawTime, (int)uu.getEffectTime());
    uu.setStartTime(nawTimeF);          //设置开始时间    (yyyy-MM-dd)
    uu.setEndTime(DateUtil.format(endTime, "yyyy-MM-dd"));  //设置到期时间    (yyyy-MM-dd)
    return uu;
  }

}
