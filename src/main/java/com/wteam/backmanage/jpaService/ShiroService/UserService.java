package com.wteam.backmanage.jpaService.ShiroService;

import com.wteam.backmanage.Entity.Shiro.User;
import com.wteam.backmanage.jpaRepository.ShiroImpl.UserServiceImpl;
import com.wteam.backmanage.jpaRepository.ShiroRepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author fantomboss
 * @date 2019/2/9-10:55
 */
@Service
public class UserService implements UserServiceImpl {

  @Autowired
  private UserRepository userRepository;

  //userId查询
  public User findById(int id) {
   return userRepository.findByUserId(id);
  }

  //查寻账号
  public User findByName(String name){
    return userRepository.findByName(name);
  }


}
