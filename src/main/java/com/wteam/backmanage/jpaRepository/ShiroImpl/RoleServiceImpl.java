package com.wteam.backmanage.jpaRepository.ShiroImpl;


import com.wteam.backmanage.Entity.Shiro.Role;

/**
 * @author fantomboss
 * @date 2019/2/9-11:39
 */
public interface RoleServiceImpl {

  Role findById(int id);
}
