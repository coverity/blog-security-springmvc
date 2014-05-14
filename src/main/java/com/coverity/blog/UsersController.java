package com.coverity.blog;

import java.util.List;
import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.coverity.blog.beans.User;
import com.coverity.blog.service.UsersService;

@Controller
@RequestMapping("users")
public class UsersController {
  // Automatically injected by Spring, we don't see a call to the
  // `UserService` ctor in the program
  @Autowired
  private UsersService users;


  // Create a new user object to be used in the POST form
  @ModelAttribute
  public User populateUserCommand() {
    return new User("name", "email", "pass");
  }


  // Create a new user object to be used in the POST form
  @ModelAttribute("latestUser")
  public User populateLatestUser() {
    return users.getLatestInsert();
  }


  // Main entry point. The `user` is taken from the ModelAttribue, but can
  // be overridden by supplied value. This could lead to XSS.
  @RequestMapping(method=RequestMethod.GET)
  public String getUsers(@ModelAttribute("user") User user) {

    (new File("/tmp", user.getName())).mkdirs(); // Generate a path manipulation issue

    return "user/list";
  }


  // Entry point, receives the user data from the POST form
  @RequestMapping(method=RequestMethod.POST)
  public String addUser(@ModelAttribute User user, Model model, BindingResult result) {
    if (result.hasErrors()) {
      // There is a binding error, so we cannot continue
      return "error";
    }

    (new File("/tmp", user.getName())).mkdirs(); // Generate a path manipulation issue

    users.addUser(user);
    return "redirect:users"; // Send a 302 (redirect) to /users (so UsersController.getUsers)
  }


  // Ajax service to find users, it returns JSON as specified in the servlet-context.xml:36
  @RequestMapping(value="/search", method=RequestMethod.GET, produces="application/json")
  public @ResponseBody List<User> findUser(String query) {

    (new File("/tmp", query)).mkdirs(); // Generate a path manipulation issue

    return users.findUser(query); // No XSS here
  }
}
