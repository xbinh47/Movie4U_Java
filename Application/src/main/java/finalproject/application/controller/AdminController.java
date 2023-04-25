package finalproject.application.controller;

import finalproject.application.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.util.HashMap;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @GetMapping("/")
    public String main() {
        return "user/index.html";
    }

    @GetMapping("/getAllSchedule")
    @ResponseBody
    public HashMap<String, Object> getAllSchedule() throws ParseException {
        return adminService.getAllSchedule();
    }
}
