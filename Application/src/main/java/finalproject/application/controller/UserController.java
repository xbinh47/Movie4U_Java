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


    @GetMapping("/movie")
    public String movie() {
        return "user/movie.html";
    }

    @GetMapping("/movieticket")
    public String movieticket() {
        return "user/movie_ticket.html";
    }

    @GetMapping("/support")
    public String support() {
        return "user/support.html";
    }


    @GetMapping("/profile")
    public String profile() {
        return "user/profile.html";
    }

    @GetMapping("/history")
    public String history() {
        return "user/history.html";
    }

    @GetMapping("/theater")
    public String theater() {
        return "user/theater.html";
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
