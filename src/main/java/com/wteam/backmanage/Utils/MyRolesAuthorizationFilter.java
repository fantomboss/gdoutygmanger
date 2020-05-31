package com.wteam.backmanage.Utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author mission
 * @date 2018/10/26 0026-20:15
 */

/**
 * AuthorizationFilter抽象类事项了javax.servlet.Filter接口，它是个过滤器。
 */
public class MyRolesAuthorizationFilter extends AuthorizationFilter {

  @Override
  protected boolean isAccessAllowed(ServletRequest req, ServletResponse resp, Object mappedValue) throws Exception {
    Subject subject = getSubject(req, resp);
    String[] rolesArray = (String[]) mappedValue;

    SecurityUtils.getSubject();

    //没有角色限制，有权限访问
    if (rolesArray == null || rolesArray.length == 0) {
      return true;
    }
    for (int i = 0; i < rolesArray.length; i++) {
      //若当前用户是rolesArray中的任何一个，则有权限访问
      if (subject.hasRole(rolesArray[i])) {
        return true;
      }
    }

    return false;
  }
}
