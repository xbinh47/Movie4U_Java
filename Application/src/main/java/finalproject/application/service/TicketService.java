package finalproject.application.service;

import java.util.HashMap;

public interface TicketService {
    HashMap<String,Object> getMovieSchedule();
    HashMap<String, Object> addTicket();
    HashMap<String, Object> getSeat();
    HashMap<String, Object> getTicketByAccountId();
    HashMap<String, Object> getFoodCombo();
    HashMap<String, Object> getAllTicket();
    HashMap<String, Object> checkSeat();
    HashMap<String, Object> addTicketAndSeatAndFoodComboList();
    HashMap<String, Object> getTicketById();

}
