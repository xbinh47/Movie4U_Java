package finalproject.application.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
public class UserController{
   @GetMapping("/")
    public String main() {
         return "admin/index.html";
    }
}
