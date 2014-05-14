package com.coverity.blog;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.ui.Model;

import com.coverity.blog.beans.User;

@Controller
public class HomeController {

  // Being polite
  @RequestMapping(value="/hello", produces="text/plain")
  public @ResponseBody String sayHello(String name) {
    return "Hello " + name + "!"; // No XSS
  }

  @RequestMapping(value="/index")
  public String index(User user, Model model) {
    model.addAttribute("current_user", user);
    return "home";
  }

}
