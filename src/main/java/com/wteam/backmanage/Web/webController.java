package com.wteam.backmanage.Web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author fantomboss
 * @date 2019/2/18-15:18
 */
@Controller
public class webController {

  @RequestMapping("/noPermission")
  public String noPermission(){
    return "/noPermission";
  }

  @RequestMapping("/login")
  public String login(){
    return "/login";
  }
}
