package finalproject.application.controller;

import finalproject.application.config.JwtService;
import finalproject.application.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class UserController{
    private final JwtService jwtService;

    @PostMapping("/login")
    @ResponseBody
    public HashMap<String, Object> login(@RequestBody HashMap<String, String> body, HttpServletRequest request) {
        HashMap<String, Object> result = userService.logIn(body,request);
        return result;
    }

    @PostMapping("/register")
    @ResponseBody
    public HashMap<String, Object> register(@RequestBody HashMap<String, String> body) {
        HashMap<String, Object> result = userService.register(body);
        return result;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) throws IOException {
        request.getSession().removeAttribute("email");
        request.getSession().removeAttribute("account_id");
        HashMap<String, Object> result = new HashMap<>();
        result.put("status", "success");
        return "redirect:/";
    }

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



    @GetMapping("/checkToken")
    @ResponseBody
    public HashMap<String, Object> checkToken(@Param("token") String token, HttpServletRequest request) {
        HashMap<String, Object> response = new HashMap<>();
        if(jwtService.isTokenExpired(token)){
            response.put("code", "401");
            response.put("message", "Token expired");
            request.getSession().removeAttribute("email");
            request.getSession().removeAttribute("account_id");
            return response;
        }
        response.put("code", "200");
        response.put("message", "Valid token");
        return response;
    }

    @PostMapping("/updateUserInfo")
    @ResponseBody
    public HashMap<String, Object> updateUserInfo(@RequestBody HashMap<String, String> body, HttpServletRequest request) {
        if(request.getSession().getAttribute("email") == null){
            HashMap<String, Object> response = new HashMap<>();
            response.put("code", "401");
            response.put("message", "Token expired");
            return response;
        }
        body.put("email", (String) request.getSession().getAttribute("email"));
        HashMap<String, Object> result = userService.updateUserInfo(body);
        return result;
    }

    @PostMapping("/changePassword")
    @ResponseBody
    public HashMap<String, Object> changePassword(@RequestBody HashMap<String, String> body, HttpServletRequest request) {
        if(request.getSession().getAttribute("email") == null){
            HashMap<String, Object> response = new HashMap<>();
            response.put("code", "401");
            response.put("message", "Token expired");
            return response;
        }
        body.put("email", (String) request.getSession().getAttribute("email"));
        HashMap<String, Object> result = userService.changePassword(body);
        return result;
    }
}
