package finalproject.application.service;

import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.HashMap;

public interface AdminService {
    HashMap<String,Object> getAllSchedule() throws ParseException;
    HashMap<String,Object> getAllTheatres();
    HashMap<String,Object> getAllUser();

}
