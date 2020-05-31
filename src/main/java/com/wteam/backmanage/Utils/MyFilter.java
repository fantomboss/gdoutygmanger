package com.wteam.backmanage.Utils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author fantomboss
 * @date 2019/2/15-15:39
 */
public class MyFilter {

  /**
   * anon：  无需登录认证
   * authc： 需要登录认证
   * user：  使用rememberMe功能记住用户，下次不用再登录
   * perms:  资源授权
   * role:   角色授权
   *
   * logout: 登出
   */
  public Map<String,String> setMyFilter(){
    Map<String,String> filterMap = new LinkedHashMap<String, String>();
    filterMap.put("/user/superLogin","anon");
    filterMap.put("/user/login","anon");
    filterMap.put("/logout","logout");

    filterMap.put("/**","authc");
    return filterMap;
  }

}
