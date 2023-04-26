package finalproject.application.controller;

import finalproject.application.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.util.HashMap;

@Controller
@RequestMapping("/ticket")
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @PostMapping("/addTicket")
    @ResponseBody
    public HashMap<String, Object> addTicket(HashMap<String, Object> data){
        return ticketService.addTicket(data);
    }
    @GetMapping("/getMovieSchedule")
    public HashMap<String, Object> getMovieSchedule(@Param("movieId") Integer movieId, @Param("date") String date) throws ParseException {
        return ticketService.getMovieSchedule(movieId, date);
    }

}
