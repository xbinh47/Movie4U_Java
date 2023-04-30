package finalproject.application.service;

import jakarta.servlet.http.HttpServletRequest;

import java.text.ParseException;
import java.util.HashMap;

public interface TicketService {
    HashMap<String,Object> getMovieSchedule(Integer movie_id, String date) throws ParseException;
    HashMap<String, Object> addTicket(HashMap<String, Object> data);
    HashMap<String, Object> getSeat(Integer schedule_time_id);
    HashMap<String, Object> getTicketByAccountId(HttpServletRequest request);
    HashMap<String, Object> getFoodCombo();
    HashMap<String, Object> getFoodComboById(Integer id);
    HashMap<String, Object> getAllTicket();
    HashMap<String, Object> checkSeat();
    HashMap<String, Object> addTicketAndSeatAndFoodComboList();
    HashMap<String, Object> getTicketById();

}
