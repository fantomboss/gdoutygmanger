package com.wteam.backmanage.jpaRepository.ShiroRepository;

import com.wteam.backmanage.Entity.Shiro.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author fantomboss
 * @date 2019/2/9-10:53
 */
public interface PermissionRepository extends JpaRepository<Permission,Integer> {

  Permission findByPermissionName(String name);
  Permission findByPermissionId(Integer id);
}
