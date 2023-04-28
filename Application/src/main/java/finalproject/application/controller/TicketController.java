package finalproject.application.controller;

import finalproject.application.service.TicketService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.HashMap;

@Controller
@RequestMapping("/ticket")
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @PostMapping("/addTicket")
    @ResponseBody
    public HashMap<String, Object> addTicket(@RequestBody HashMap<String, Object> data, HttpServletRequest request){
        if(request.getSession().getAttribute("email") != null){
            data.put("account_id", request.getSession().getAttribute("account_id"));
        }
        return ticketService.addTicket(data);
    }
    @GetMapping("/getMovieSchedule")
    @ResponseBody
    public HashMap<String, Object> getMovieSchedule(@Param("movie_id") Integer movie_id ,@Param("date") String date) throws ParseException {
        return ticketService.getMovieSchedule(movie_id, date);
    }

    @GetMapping("/getSeat")
    @ResponseBody
    public HashMap<String, Object> getSeat(@Param("schedule_time_id") Integer schedule_time_id){
        return ticketService.getSeat(schedule_time_id);
    }

    @GetMapping("/getFoodCombo")
    @ResponseBody
    public HashMap<String, Object> getFoodCombo(){
        return ticketService.getFoodCombo();
    }

}
