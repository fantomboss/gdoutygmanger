package com.wteam.backmanage.Config;

import com.wteam.backmanage.Utils.JwtFilter;
import com.wteam.backmanage.Utils.MyFilter;
import com.wteam.backmanage.Utils.MyRolesAuthorizationFilter;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

import java.util.Map;

/**
 * @author fantomboss
 * @date 2019/10/26-16:57
 */

@Configuration
public class ShiroConfig {
  @Bean
  public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultSecurityManager securityManager){
    ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
    //关联securityManager安全管理器
    shiroFilterFactoryBean.setSecurityManager(securityManager);

    //设置过滤器权限
    Map<String,String> filterMap = new MyFilter().setMyFilter();
    //自定义Filter规则
    Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();
    filters.put("shiroCors",new JwtFilter());
    filters.put("rolersOr",new MyRolesAuthorizationFilter());
    shiroFilterFactoryBean.setFilters(filters);

    shiroFilterFactoryBean.setUnauthorizedUrl("/noPermission");
    shiroFilterFactoryBean.setLoginUrl("/login");

    shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
    return shiroFilterFactoryBean;
  }

  /**
   * 创建 DefaultWebSecurityManager
   */
  @Bean(name = "securityManager")
  public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("shiroRealm") ShiroRealm realm){
    DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
    //关联Reaml
    securityManager.setRealm(realm);

    /*
     * 关闭shiro自带的session，详情见文档
     * http://shiro.apache.org/session-management.html#SessionManagement-StatelessApplications%28Sessionless%29
     */
    /*DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
    DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
    defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
    subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
    securityManager.setSubjectDAO(subjectDAO);*/

    return  securityManager;
  }

  /**
   * 创建 Realm
   */
  @Bean(name = "shiroRealm")
  public ShiroRealm getRealm(){
    return new ShiroRealm();
  }
}
