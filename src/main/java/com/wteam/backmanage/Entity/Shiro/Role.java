package com.wteam.backmanage.Entity.Shiro;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

/**
 * @author fantomboss
 * @date 2019/2/14-17:15
 */
@Entity
@Data
public class Role {

  @Id
  @GeneratedValue
  private int roleId;

  private String roleName;
  private String roleDescribe;

  @ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.MERGE})
  @JoinTable(name = "role_permission",joinColumns = @JoinColumn(name = "role_id"),inverseJoinColumns = @JoinColumn(name = "permission_id"))
  private Set<Permission> permissions;
}

