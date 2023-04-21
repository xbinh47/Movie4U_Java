package finalproject.application.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin")
public class AdminController {
    @GetMapping("/")
    public String main() {
        return "user/index.html";
    }
}
