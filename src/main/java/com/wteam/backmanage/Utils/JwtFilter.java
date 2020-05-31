package com.wteam.backmanage.Utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author mission
 * @date 2018/11/10 0010-16:12
 */
@Slf4j
public class JwtFilter extends UserFilter {


/*  *//**
   * 执行登录认证
   *
   * @param request
   * @param response
   * @param mappedValue
   * @return
   *//*
  @Override
  protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
    try {
      //executeLogin(request, response);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  *//**
   * 对跨域提供支持
   *//*
  protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
    HttpServletRequest httpRequest = WebUtils.toHttp(request);
    HttpServletResponse httpResponse = WebUtils.toHttp(response);
    if (httpRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
      httpResponse.setHeader("Access-control-Allow-Origin", httpRequest.getHeader("Origin"));
      httpResponse.setHeader("Access-Control-Allow-Methods", httpRequest.getMethod());
      httpResponse.setHeader("Access-Control-Allow-Headers", httpRequest.getHeader("Access-Control-Request-Headers"));
      httpResponse.setStatus(HttpStatus.OK.value());
      return false;
    }
    return super.preHandle(request, response);
  }*/


  /**
   * 在第一次访问过来的时候检测是否为OPTIONS请求，如果是就直接返回true，即直接放行
   */
  @Override
  protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
    HttpServletResponse httpResponse = (HttpServletResponse) response;
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    if (httpRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
      setHeader(httpRequest,httpResponse);
      return true;
    }
    return super.preHandle(request,response);
  }

  /**
   * 如果请求为其他的请求方式
   * 该方法会在验证失败后调用，这里由于是前后端分离，后台不控制页面跳转
   * 因此重写改成传输JSON数据
   */
  @Override
  protected void saveRequestAndRedirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
    saveRequest(request);
    setHeader((HttpServletRequest) request,(HttpServletResponse) response);
    PrintWriter out = response.getWriter();
    out.println("你还没有登录");
    out.flush();
    out.close();
  }

  /**
   * 为response设置header，实现跨域
   */
  private void setHeader(HttpServletRequest request, HttpServletResponse response){
    //跨域的header设置
    response.setHeader("Access-control-Allow-Origin", request.getHeader("Origin"));
    response.setHeader("Access-Control-Allow-Methods", request.getMethod());
    response.setHeader("Access-Control-Allow-Credentials", "true");
    response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
    //防止乱码，适用于传输JSON数据
    response.setHeader("Content-Type","application/json;charset=UTF-8");
    response.setStatus(HttpStatus.OK.value());
  }

}
