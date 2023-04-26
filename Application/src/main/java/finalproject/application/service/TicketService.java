package finalproject.application.service;

import java.text.ParseException;
import java.util.HashMap;

public interface TicketService {
    HashMap<String,Object> getMovieSchedule(Integer movie_id, String date) throws ParseException;
    HashMap<String, Object> addTicket(HashMap<String, Object> data);
    HashMap<String, Object> getSeat();
    HashMap<String, Object> getTicketByAccountId();
    HashMap<String, Object> getFoodCombo();
    HashMap<String, Object> getAllTicket();
    HashMap<String, Object> checkSeat();
    HashMap<String, Object> addTicketAndSeatAndFoodComboList();
    HashMap<String, Object> getTicketById();

}
