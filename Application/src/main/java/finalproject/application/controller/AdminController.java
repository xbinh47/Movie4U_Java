package finalproject.application.controller;

import finalproject.application.service.AdminService;
import finalproject.application.service.MovieService;
import finalproject.application.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.HashMap;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private MovieService movieService;
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

    @GetMapping("/getRevenue")
    @ResponseBody
    public HashMap<String, Object> getRevenue() throws ParseException {
        return adminService.getRevenue();
    }

    @GetMapping("/getAllSchedule")
    @ResponseBody
    public HashMap<String, Object> getAllSchedule() throws ParseException {
        return adminService.getAllSchedule();
    }

    @PostMapping("/addSchedule")
    @ResponseBody
    public HashMap<String, Object> addSchedule(@RequestBody HashMap<String, Object> params) throws ParseException {
        return adminService.addSchedule(params);
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

    @PostMapping("/addTheatre")
    @ResponseBody
    public HashMap<String, Object> addTheatre(@RequestParam MultipartFile image, @RequestParam HashMap<String, Object> params) throws ParseException, IOException, URISyntaxException {
        return adminService.addTheatre(image,params);
    }

    @GetMapping("/getTheatreById")
    @ResponseBody
    public HashMap<String, Object> getTheatreById(@Param("theatre_id") Integer theatre_id) throws ParseException {
        return adminService.getTheatreById(theatre_id);
    }

    @PostMapping("/updateTheatre")
    @ResponseBody
    public HashMap<String, Object> updateTheatre(@RequestParam(required = false) MultipartFile image, @RequestParam HashMap<String, Object> params) throws ParseException, IOException, URISyntaxException {
        return adminService.updateTheatre(image,params);
    }

    @DeleteMapping("/deleteTheatre")
    @ResponseBody
    public HashMap<String, Object> deleteTheatre(@Param("id") Integer id) throws ParseException {
        return adminService.deleteTheatre(id);
    }

    @GetMapping("/getAllUser")
    @ResponseBody
    public HashMap<String, Object> getAllUser() throws ParseException {
        return adminService.getAllUser();
    }

    @GetMapping("/getAllMovie")
    @ResponseBody
    public HashMap<String, Object> getAllMovies() throws ParseException {
        return movieService.getAllMovie();
    }

    @GetMapping("/getMovieById")
    @ResponseBody
    public HashMap<String, Object> getMovieById(@Param("id") Integer id) throws ParseException {
        return movieService.getMovieById(id);
    }

    @PostMapping("/addMovie")
    @ResponseBody
    public HashMap<String, Object> addMovie(@RequestParam(required = false) MultipartFile image, @RequestParam HashMap<String, Object> params) throws ParseException, IOException, URISyntaxException {
        return adminService.addMovie(image,params);
    }

    @PostMapping("/updateMovie")
    @ResponseBody
    public HashMap<String, Object> updateMovie(@RequestParam(required = false) MultipartFile image, @RequestParam HashMap<String, Object> params) throws ParseException, IOException, URISyntaxException {
        return adminService.updateMovie(image,params);
    }

    @DeleteMapping("/deleteMovie")
    @ResponseBody
    public HashMap<String, Object> deleteMovie(@Param("id") Integer id) throws ParseException {
        return adminService.deleteMovie(id);
    }

    @GetMapping("/getFoodComboById")
    @ResponseBody
    public HashMap<String, Object> getFoodComboById(@Param("id") Integer id) throws ParseException {
        return ticketService.getFoodComboById(id);
    }

    @PostMapping("/addFoodCombo")
    @ResponseBody
    public HashMap<String, Object> addFoodCombo(@RequestParam(required = false) MultipartFile image, @RequestParam HashMap<String, Object> params) throws ParseException, IOException, URISyntaxException {
        return adminService.addFoodCombo(image,params);
    }

    @PostMapping("/updateFoodCombo")
    @ResponseBody
    public HashMap<String, Object> updateFoodCombo(@RequestParam(required = false) MultipartFile image, @RequestParam HashMap<String, Object> params) throws ParseException, IOException, URISyntaxException {
        return adminService.updateFoodCombo(image,params);
    }

    @DeleteMapping("/deleteFoodCombo")
    @ResponseBody
    public HashMap<String, Object> deleteFoodCombo(@Param("id") Integer id) throws ParseException {
        return adminService.deleteFoodCombo(id);
    }
}
