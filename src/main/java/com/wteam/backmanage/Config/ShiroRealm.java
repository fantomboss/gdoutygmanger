package com.wteam.backmanage.Config;

import com.wteam.backmanage.Entity.Shiro.Permission;
import com.wteam.backmanage.Entity.Shiro.Role;
import com.wteam.backmanage.Entity.Shiro.User;
import com.wteam.backmanage.jpaRepository.ShiroImpl.RoleServiceImpl;
import com.wteam.backmanage.jpaRepository.ShiroImpl.UserServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * @author fantomboss
 * @date 2019/2/8-11:40
 */
public class ShiroRealm extends AuthorizingRealm {

  @Autowired
  private UserServiceImpl userService;
  @Autowired
  private RoleServiceImpl roleService;

  //授权
  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
    System.out.println("调用授权方法");

    //授权info
    SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
    //角色信息
    Set<String> infoRoles = new HashSet<String>();
    Set<String> infoPermissions = new HashSet<String>();

    Subject subject = SecurityUtils.getSubject();
    User user = (User)subject.getPrincipal(); //获取登录对象
    Set<Role> roles = user.getRoles();
    for(Role role:roles){
      Set<Permission> permissions = role.getPermissions();
      for(Permission permission:permissions){
        //加入授权信息
        infoPermissions.add(permission.getPermissionName());
      }
      //加入角色信息
      infoRoles.add(role.getRoleName());
    }

    System.out.println("Roles: "+infoRoles);
    System.out.println("Permission: "+infoPermissions);
    info.setRoles(infoRoles);
    info.setStringPermissions(infoPermissions);

    return info;
  }

  //认证
  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
    System.out.println("调用认证方法");

    //从数据库中提出来等等账号密码

    //1、判断用户名
    UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
    User user = userService.findByName(token.getUsername());
      if (user == null) {
        //用户名不存在
        return null;    //shiro的底层会报错
      }
      //2、判断密码
      return new SimpleAuthenticationInfo(user,userService.findByName(token.getUsername()).getPsw(),"");
  }
}
