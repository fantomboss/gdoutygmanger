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
public class User {

  @Id
  @GeneratedValue
  private int userId;

  private String name;
  private String psw;
  private String cpuId;
  private String startTime; //开始时间
  private long effectTime;//有效时长
  private String endTime;//失效时间

  @ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.MERGE})
  @JoinTable(name = "user_role",joinColumns = @JoinColumn(name = "user_id"),inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles;

}
