package com.wteam.backmanage.Entity.Shiro;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author fantomboss
 * @date 2019/2/14-17:16
 */
@Data
@Entity
public class Permission {

  @Id
  @GeneratedValue
  private int permissionId;

  private String permissionName;
  private String permissionDescribe;
}
