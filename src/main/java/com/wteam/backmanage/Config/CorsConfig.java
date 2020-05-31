package com.wteam.backmanage.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author fantomboss
 * @date 2019/1/27-10:22
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    //设置允许跨域的路径
    registry.addMapping("/**")
            //设置允许跨域请求的域名
            .allowedOrigins("*")
            //是否允许证书 不再默认开启
            .allowCredentials(true)
            //设置允许的方法
            .allowedMethods("*")
            .allowedHeaders("*")
            //跨域允许时间
            .maxAge(3600)
            //跨域中请求头允许的内容标识
            .exposedHeaders("Vcode","Authorization",
                    "access-control-allow-headers" ,
                    "access-control-allow-methods" ,
                    "access-control-allow-origin" ,
                    "access-control-max-age" ,
                    "X-Frame-Options");
  }
}
