package com.wteam.backmanage.jpaService.ShiroService;

import com.wteam.backmanage.Entity.Shiro.Role;
import com.wteam.backmanage.jpaRepository.ShiroImpl.RoleServiceImpl;
import com.wteam.backmanage.jpaRepository.ShiroRepository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author fantomboss
 * @date 2019/2/9-10:56
 */
@Service
public class RoleService implements RoleServiceImpl {

  @Autowired
  private RoleRepository roleRepository;

  @Override
  public Role findById(int id) {
    return roleRepository.findByRoleId(id);
  }
}
