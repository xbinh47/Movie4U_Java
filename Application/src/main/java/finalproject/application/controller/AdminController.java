package finalproject.application.controller;

import finalproject.application.service.AdminService;
import finalproject.application.service.TicketService;
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
    @Autowired
    private TicketService ticketService;
    @GetMapping("")
    public String main() {
        return "admin/dashboard_admin.html";
    }

    @GetMapping("/Booking")
    public String booking() {
        return "admin/booking_admin.html";
    }


    @GetMapping("/MovieTheatres")
    public String movieTheatres() {
        return "admin/movietheatres_admin.html";
    }


    @GetMapping("/Movies")
    public String movie() {
        return "admin/movies_admin.html";
    }

    @GetMapping("/ShowTiming")
    public String showTiming() {
        return "admin/showtiming_admin.html";
    }

    @GetMapping("/Combo")
    public String combo() {
        return "admin/combo_admin.html";
    }

    @GetMapping("/Users")
    public String user() {
        return "admin/users_admin.html";
    }

    @GetMapping("/getAllSchedule")
    @ResponseBody
    public HashMap<String, Object> getAllSchedule() throws ParseException {
        return adminService.getAllSchedule();
    }

    @GetMapping("/getAllTicket")
    @ResponseBody
    public HashMap<String, Object> getAllTicket() throws ParseException {
        return ticketService.getAllTicket();
    }

    @GetMapping("/getAllTheatres")
    @ResponseBody
    public HashMap<String, Object> getAllTheatres() throws ParseException {
        return adminService.getAllTheatres();
    }

    @GetMapping("/getAllUser")
    @ResponseBody
    public HashMap<String, Object> getAllUser() throws ParseException {
        return adminService.getAllUser();
    }
}
