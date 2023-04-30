package finalproject.application.serviceImpl;

import finalproject.application.dto.ScheduleDTO;
import finalproject.application.dto.TicketDTO;
import finalproject.application.entity.*;
import finalproject.application.repository.*;
import finalproject.application.service.EmailService;
import finalproject.application.service.TicketService;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.EntityManagerProxy;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
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
    @Autowired
    private EmailService emailService;

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
        if(data.get("schedule_id") == null || data.get("schedule_time_id") == null || data.get("seat") == null){
            HashMap<String, Object> result = new HashMap<>();
            result.put("code", "400");
            result.put("message", "Please fill in all the fields");
            return result;
        }

        String[] foodComboIdList = data.get("food_combo_id").toString().split(",");
        String[] foodComboQuantityList = data.get("food_combo_quantity").toString().split(",");
        Account account = accountRepository.getAccountById(Integer.parseInt(data.get("account_id").toString()));
        ScheduleTime scheduleTime = scheduleTimeRepository.getScheduleTimeById(Integer.parseInt(data.get("schedule_time_id").toString()));

        String[] seat = data.get("seat").toString().split(",");

        if(!checkSeat(seat, scheduleTime)){
            HashMap<String, Object> result = new HashMap<>();
            result.put("code", "400");
            result.put("message", "Seat is already taken");
            return result;
        }

        Ticket newTicket = new Ticket();

//      Add Schedule Time
        newTicket.setScheduleTime(scheduleTime);

//      Add Account
        LocalDateTime now = LocalDateTime.now();
        newTicket.setCreateat(now);
        newTicket.setAccount(account);

        newTicket.setTotal(0);
        ticketRepository.save(newTicket);

//      Add Food Combo And calculate price
        Integer totalPrice = 0;
        if(foodComboIdList.length > 0 && !foodComboIdList[0].equals("")){
            List<FoodComboTicket> foodComboTicketList = new ArrayList<>();
            for(int i = 0; i < foodComboIdList.length; i++){
                FoodComboTicket foodComboTicket = new FoodComboTicket();
                FoodCombo foodCombo = foodComboRepository.findFoodComboById(Integer.parseInt(foodComboIdList[i]));
                foodComboTicket.setFoodCombo(foodCombo);
                totalPrice += foodCombo.getPrice() * Integer.parseInt(foodComboQuantityList[i]);
                foodComboTicket.setQuantity(Integer.parseInt(foodComboQuantityList[i]));
                foodComboTicket.setTicket(newTicket);
                foodComboTicketList.add(foodComboTicket);
            }
            newTicket.setFoodComboTicketList(foodComboTicketList);
        }

//      Add Schedule Price
        Schedule schedule = scheduleRepository.getScheduleById(Integer.parseInt(data.get("schedule_id").toString()));
        totalPrice += schedule.getPrice();

        newTicket.setTotal(totalPrice);

        ticketRepository.save(newTicket);

//      Add Seat
        for(int i = 0; i < seat.length; i++){
            Seat newSeat = new Seat();
            newSeat.setName(seat[i]);
            newSeat.setScheduleTime(scheduleTime);
            newSeat.setTicket(newTicket);
            seatRepository.save(newSeat);
        }


            try {
                emailService.sendTicket(account.getEmail(), "Thank for purchase" , ticketRepository.findTicketById(newTicket.getId()));
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        CompletableFuture.runAsync(() -> {

            System.out.println("Hello");
        });

        HashMap<String, Object> result = new HashMap<>();
        result.put("code", "200");
        result.put("message", "Success");
        return result;
    }

    @Override
    public HashMap<String, Object> getSeat(Integer schedule_time_id) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("code", "200");
        result.put("message", "Success");
        Object[] seatList = seatRepository.getSeatByScheduleTimeId(schedule_time_id);
        HashMap<String, Object> seat_names = new HashMap<>();
        if(seatList[0] == null ){
            seat_names.put("seat_names", "");
        }else{
            seat_names.put("seat_names", seatList[0]);
        }
        result.put("data", seat_names);
        return result;

    }

    @Override
    public HashMap<String, Object> getTicketByAccountId(HttpServletRequest request) {
        if(request.getSession().getAttribute("account_id") == null){
            HashMap<String, Object> result = new HashMap<>();
            result.put("code", "400");
            result.put("message", "Please login");
            return result;
        }

        Integer account_id = Integer.parseInt(request.getSession().getAttribute("account_id").toString());
        List<Object[]> ticketList = ticketRepository.getTicketByAccountId(account_id);
        List<TicketDTO> ticketDTOList = new ArrayList<>();
        for(Object[] ticket : ticketList){
            ticketDTOList.add(TicketDTO.getInstance().convertToObject(ticket));
        }

        HashMap<String, Object> result = new HashMap<>();
        result.put("code", "200");
        result.put("message", "Success");
        result.put("data", ticketDTOList);
        return result;
    }

    @Override
    public HashMap<String, Object> getFoodCombo() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("code", "200");
        result.put("message", "Success");
        result.put("data", foodComboRepository.findAll());
        return result;
    }

    @Override
    public HashMap<String, Object> getFoodComboById(Integer id) {
        HashMap<String, Object> result = new HashMap<>();
        List<FoodCombo> foodComboList = new ArrayList<>();
        foodComboList.add(foodComboRepository.findFoodComboById(id));
        result.put("code", "200");
        result.put("message", "Success");
        result.put("data", foodComboList);
        return result;
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

    public boolean checkSeat(String[] seat, ScheduleTime scheduleTime){
        for(String s : seat){
            if(seatRepository.getSeatByNameAndScheduleTime(s, scheduleTime) != null){
                return false;
            }
        }
        return true;
    }
}
