package finalproject.application.serviceImpl;

import finalproject.application.dto.ScheduleDTO;
import finalproject.application.entity.Schedule;
import finalproject.application.repository.ScheduleRepository;
import finalproject.application.service.TicketService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {
    @Autowired
    private ScheduleRepository scheduleRepository;
    private ModelMapper modelMapper;
    @Override
    public HashMap<String, Object> getMovieSchedule() {
        List<Object[]> scheduleList = scheduleRepository.getAllSchedule();
        HashMap<String, Object> result = new HashMap<>();
        result.put("code", "200");
        result.put("message", "Success");
        result.put("data", scheduleList);
        return result;
    }

    @Override
    public HashMap<String, Object> addTicket() {
        return null;
    }

    @Override
    public HashMap<String, Object> getSeat() {
        return null;
    }

    @Override
    public HashMap<String, Object> getTicketByAccountId() {
        return null;
    }

    @Override
    public HashMap<String, Object> getFoodCombo() {
        return null;
    }

    @Override
    public HashMap<String, Object> getAllTicket() {
        return null;
    }

    @Override
    public HashMap<String, Object> checkSeat() {
        return null;
    }

    @Override
    public HashMap<String, Object> addTicketAndSeatAndFoodComboList() {
        return null;
    }

    @Override
    public HashMap<String, Object> getTicketById() {
        return null;
    }
}
