package finalproject.application.serviceImpl;

import finalproject.application.dto.ScheduleDTO;
import finalproject.application.dto.TheatreDTO;
import finalproject.application.repository.AccountRepository;
import finalproject.application.repository.ScheduleRepository;
import finalproject.application.repository.TheatreRepository;
import finalproject.application.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private TheatreRepository theatreRepository;
    @Autowired
    private AccountRepository accountRepository;
    private ModelMap modelMap;
    @Override
    public HashMap<String, Object> getAllSchedule() throws ParseException {
        HashMap<String, Object> result = new HashMap<>();
        result.put("code", "200");
        result.put("message", "Success");
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();
        for (Object[] schedule : scheduleRepository.getAllSchedule()) {
            scheduleDTOList.add(ScheduleDTO.getInstance().convertToObject(schedule));
        }
        result.put("data", scheduleDTOList);
        return result;
    }

    @Override
    public HashMap<String, Object> getAllTheatres() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("code", "200");
        result.put("message", "Success");
        List<Object[]> theatreList = theatreRepository.getAllTheatre();
        List<TheatreDTO> theatreDTOList = new ArrayList<>();
        for (Object[] theatre : theatreList) {
            theatreDTOList.add(TheatreDTO.getInstance().convertToObject(theatre));
        }
        result.put("data", theatreDTOList);
        return result;
    }

    @Override
    public HashMap<String, Object> getAllUser() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("code", "200");
        result.put("message", "Success");
        result.put("data", accountRepository.findAll());
        return result;
    }


}
