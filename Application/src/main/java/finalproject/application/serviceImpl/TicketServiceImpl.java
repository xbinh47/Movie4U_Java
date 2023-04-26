package finalproject.application.serviceImpl;

import finalproject.application.dto.ScheduleDTO;
import finalproject.application.dto.TicketDTO;
import finalproject.application.entity.*;
import finalproject.application.repository.*;
import finalproject.application.service.TicketService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private ScheduleTimeRepository scheduleTimeRepository;
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private FoodComboRepository foodComboRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private AccountRepository accountRepository;
    private ModelMapper modelMapper;
    @Override
    public HashMap<String, Object> getMovieSchedule(Integer movie_id, String date) throws ParseException {
        List<Object[]> scheduleList = scheduleRepository.getScheduleByMovieIdAndDate(movie_id, date);
        HashMap<String, Object> result = new HashMap<>();
        result.put("code", "200");
        result.put("message", "Success");
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();
        for(Object[] schedule : scheduleList){
            scheduleDTOList.add(ScheduleDTO.getInstance().convertToObject(schedule));
        }
        result.put("data", scheduleDTOList);
        return result;
    }

    @Override
    public HashMap<String, Object> addTicket(HashMap<String, Object> data) {
        if(data.get("schedule_id") == null || data.get("schedule_time id") == null || data.get("seat") == null){
            HashMap<String, Object> result = new HashMap<>();
            result.put("code", "400");
            result.put("message", "Please fill in all the fields");
            return result;

        }

        String[] seat = data.get("seat").toString().split(",");
        checkSeat(seat);

        if(!checkSeat(seat)){
            HashMap<String, Object> result = new HashMap<>();
            result.put("code", "400");
            result.put("message", "Seat is already taken");
            return result;
        }

        String[] foodComboIdList = data.get("food_combo_id").toString().split(",");
        String[] foodComboQuantityList = data.get("food_combo_quantity").toString().split(",");

        Ticket newTicket = new Ticket();

//      Add Account
        newTicket.setAccount(accountRepository.getAccountById(Integer.parseInt(data.get("account_id").toString())));

//      Add Seat
        List<Seat> seatList = new ArrayList<>();
        for(int i = 0; i < seat.length; i++){
            seatList.add(seatRepository.getSeatByName(seat[i]));
        }
        newTicket.setSeatList(seatList);

//      Add Food Combo And calculate price
        Integer totalPrice = 0;
        if(foodComboIdList.length > 0 && !foodComboIdList[0].equals("")){
            List<FoodComboTicket> foodComboTicketList = new ArrayList<>();
            for(int i = 0; i < foodComboIdList.length; i++){
                FoodComboTicket foodComboTicket = new FoodComboTicket();
                FoodCombo foodCombo = foodComboRepository.getFoodComboById(Integer.parseInt(foodComboIdList[i]));
                foodComboTicket.setFoodCombo(foodCombo);
                totalPrice += foodCombo.getPrice() * Integer.parseInt(foodComboQuantityList[i]);
                foodComboTicket.setQuantity(Integer.parseInt(foodComboQuantityList[i]));
                foodComboTicketList.add(foodComboTicket);
            }
            newTicket.setFoodComboTicketList(foodComboTicketList);
        }

//       Add Schedule Price
        Schedule schedule = scheduleRepository.getScheduleById(Integer.parseInt(data.get("schedule_id").toString()));
        totalPrice += schedule.getPrice();

//      Add Schedule Time
        ScheduleTime scheduleTime = scheduleTimeRepository.getScheduleTimeById(Integer.parseInt(data.get("schedule_time_id").toString()));
        newTicket.setScheduleTime(scheduleTime);
        newTicket.setTotal(totalPrice);

        Ticket resultTicket =  ticketRepository.save(newTicket);

        HashMap<String, Object> result = new HashMap<>();
        result.put("code", "200");
        result.put("message", "Success");
        result.put("data", TicketDTO.getInstance().convertToObject(ticketRepository.getTicketDetails(resultTicket.getId()).get(0)));
        return result;
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
        ticketRepository.getAllTicket();
        HashMap<String, Object> result = new HashMap<>();
        result.put("code", "200");
        result.put("message", "Success");
        List<TicketDTO> ticketDTOList = new ArrayList<>();
        for(Object[] ticket : ticketRepository.getAllTicket()){
            ticketDTOList.add(TicketDTO.getInstance().convertToObject(ticket));
        }
        result.put("data", ticketDTOList);
        return result;
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

    public boolean checkSeat(String[] seat){
        for(String s : seat){
            if(seatRepository.getSeatByName(s) != null){
                return false;
            }
        }
        return true;
    }
}
