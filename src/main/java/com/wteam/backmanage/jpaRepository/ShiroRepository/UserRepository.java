package com.wteam.backmanage.jpaRepository.ShiroRepository;

import com.wteam.backmanage.Entity.Shiro.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author fantomboss
 * @date 2019/2/9-10:52
 */
public interface UserRepository extends JpaRepository<User,Integer> {

  User findByUserId(int id);

  User findByName(String name);

  Page<User> findByNameLike(String name, Pageable pageable);
}
