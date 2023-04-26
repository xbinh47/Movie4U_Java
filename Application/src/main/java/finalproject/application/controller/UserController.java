package finalproject.application.controller;

import finalproject.application.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
@Controller
@RequestMapping("")
public class UserController{
    @Autowired
    private UserService userService;
    @GetMapping("/")
    public String main() {
         return "user/home.html";
    }
    @PostMapping("/login")
    @ResponseBody
    public HashMap<String, Object> login(@RequestBody HashMap<String, String> body, HttpServletRequest request) {
        HashMap<String, Object> response = new HashMap<>();
        userService.logIn(body);
        return response;
    }

    @GetMapping("/checkToken")
    @ResponseBody
    public HashMap<String, Object> checkToken(@Param("token") String token, HttpServletRequest request) {
        HashMap<String, Object> response = new HashMap<>();
        response.put("code", "200");
        response.put("message", "Login successfully");
        return response;
    }
}
