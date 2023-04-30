package finalproject.application.serviceImpl;

import finalproject.application.dto.ScheduleDTO;
import finalproject.application.dto.TheatreDTO;
import finalproject.application.entity.*;
import finalproject.application.repository.*;
import finalproject.application.service.AdminService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private TheatreRepository theatreRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private FoodComboRepository foodComboRepository;
    @Autowired
    private ScheduleTimeRepository scheduleTimeRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Override
    public HashMap<String, Object> getRevenue() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("code", "200");
        result.put("message", "Success");
        Integer totalRevenue = ticketRepository.getTotalRevenue();
        Integer totalTicket = ticketRepository.getTotalTicket();
        Integer totalSeat = seatRepository.getTotalSeat();
        if(totalRevenue == null){
            totalRevenue = 0;
        }
        HashMap<String, Object> data = new HashMap<>();
        data.put("revenue", totalRevenue);
        data.put("totalTicket", totalTicket);
        data.put("toltalView", totalSeat);
        result.put("data", data);
        return result;
    }

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
    public HashMap<String, Object> addSchedule(HashMap<String, Object> params) {
        if(params.get("movie_id") == null || params.get("theatre_id") == null || params.get("price") == null || params.get("date") == null || params.get("start_time") == null || params.get("end_time") == null || params.get("room_id") == null){
            HashMap<String, Object> result = new HashMap<>();
            result.put("code", "400");
            result.put("message", "Missing params");
            return result;
        }

        Integer movie_id = Integer.parseInt(params.get("movie_id").toString());
        Integer theatre_id = Integer.parseInt(params.get("theatre_id").toString());
        Integer price = Integer.parseInt(params.get("price").toString());
        String date = params.get("date").toString();
        String start_time = params.get("start_time").toString();
        String end_time = params.get("end_time").toString();
        Integer room_id = Integer.parseInt(params.get("room_id").toString());

        if(movieRepository.getMovieById(movie_id).size() == 0){
            HashMap<String, Object> result = new HashMap<>();
            result.put("code", "400");
            result.put("message", "Movie not found");
            return result;
        }

        if(theatreRepository.getTheatreById(theatre_id).size() == 0){
            HashMap<String, Object> result = new HashMap<>();
            result.put("code", "400");
            result.put("message", "Theatre not found");
            return result;
        }

        if(!checkDate(date)){
            HashMap<String, Object> result = new HashMap<>();
            result.put("code", "400");
            result.put("message", "Date must be after today");
            return result;
        }

        if(!checkTime(start_time, end_time)){
            HashMap<String, Object> result = new HashMap<>();
            result.put("code", "400");
            result.put("message", "Start time must be before end time");
            return result;
        }

        Movie movie = movieRepository.findMovieById(movie_id);

        if(movie.getDuration() > getDurationInMinutes(start_time, end_time)){
            HashMap<String, Object> result = new HashMap<>();
            result.put("code", "400");
            result.put("message", "The duration of the schedule must be greater than the duration of the movie");
            return result;
        }

        if(!isScheduleTimeAvailable(start_time, end_time, date, room_id)){
            HashMap<String, Object> result = new HashMap<>();
            result.put("code", "400");
            result.put("message", "Schedule time is not available");
            return result;
        }

        Schedule schedule = new Schedule();
        schedule.setMovie(movie);
        schedule.setDate(LocalDate.parse(date));
        schedule.setTheatre(theatreRepository.findTheatreById(theatre_id));
        schedule.setPrice(price);
        schedule.setRoom(roomRepository.findRoomById(room_id));
        scheduleRepository.save(schedule);

        ScheduleTime scheduleTime = new ScheduleTime();
        scheduleTime.setStartTime(start_time);
        scheduleTime.setEndTime(end_time);
        scheduleTime.setSchedule(schedule);
        scheduleTimeRepository.save(scheduleTime);

        HashMap<String, Object> result = new HashMap<>();
        result.put("code", "200");
        result.put("message", "Success");
        return result;
    }

    private Integer getDurationInMinutes(String startTime, String endTime) {
        LocalTime start = LocalTime.parse(startTime);
        LocalTime end = LocalTime.parse(endTime);

        Integer durationInMinutes = Math.toIntExact(Duration.between(start, end).toMinutes());
        return durationInMinutes;
    }

    private boolean isScheduleTimeAvailable(String startTime, String endTime, String date, Integer roomId) {
        int count = scheduleTimeRepository.countScheduleTimeByStartTimeAndEndTimeAndDateAndRoom(startTime, endTime, date, roomId);
        return count == 0;
    }

    private boolean checkTime(String startTime, String endTime) {
        LocalTime start = LocalTime.parse(startTime);
        LocalTime end = LocalTime.parse(endTime);
        if(start.isBefore(end)){
            return true;
        }
        return false;
    }

    private boolean checkDate(String date) {
        LocalDate localDate = LocalDate.parse(date);
        LocalDate now = LocalDate.now();
        if(localDate.isBefore(now)){
            return false;
        }
        return true;
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
    public HashMap<String, Object> getTheatreById(Integer id) {
        Object[] theatre = theatreRepository.getTheatreById(id).get(0);
        List<TheatreDTO> theatreDTOList = new ArrayList<>();
        theatreDTOList.add(TheatreDTO.getInstance().convertToObject(theatre));
        HashMap<String, Object> result = new HashMap<>();
        result.put("code", "200");
        result.put("message", "Success");
        result.put("data", theatreDTOList);
        return result;
    }

    @Override
    public HashMap<String, Object> addTheatre(MultipartFile image, HashMap<String, Object> params) throws IOException, URISyntaxException {
        if(params.get("name") == null || params.get("address") == null || params.get("description") == null || params.get("tel") == null || params.get("R2D_3D") == null || params.get("R4DX") == null || params.get("RIMAX") == null){
            HashMap<String, Object> result = new HashMap<>();
            result.put("code", "400");
            result.put("message", "Missing params");
            return result;
        }

        if(image == null){
            HashMap<String, Object> result = new HashMap<>();
            result.put("code", "400");
            result.put("message", "Missing image");
            return result;
        }

        String name = params.get("name").toString();
        String address = params.get("address").toString();
        String description = params.get("description").toString();
        String tel = params.get("tel").toString();
        Integer R2D_3D = Integer.parseInt(params.get("R2D_3D").toString());
        Integer R4DX = Integer.parseInt(params.get("R4DX").toString());
        Integer RIMAX = Integer.parseInt(params.get("RIMAX").toString());
        String fileName = image.getOriginalFilename();

        String[] split = fileName.split("\\.");
        String fileExtension = split[split.length - 1];
        if(!fileExtension.equals("jpg") && !fileExtension.equals("png") && !fileExtension.equals("jpeg") && !fileExtension.equals("gif") && !fileExtension.equals("bmp") && !fileExtension.equals("svg") && !fileExtension.equals("webp")){
            HashMap<String, Object> result = new HashMap<>();
            result.put("code", "400");
            result.put("message", "Wrong file extension");
            return result;
        }

        ClassLoader classLoader = getClass().getClassLoader();
        URI uri = classLoader.getResource("static/images/MovieTheatres").toURI();
        Path imagePath = Paths.get(uri).resolve(fileName);
        Files.copy(image.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);


        Theatre theatre = new Theatre();
        theatre.setName(name);
        theatre.setAddress(address);
        theatre.setDescription(description);
        theatre.setTel(tel);
        theatre.setImage("/images/MovieTheatres/" + fileName);

        Integer count = 0;

        for(int i = 0; i < R2D_3D; i++){
            Room room = new Room();
            room.setName("Room " + count++);
            room.setTheatre(theatre);
            room.setType("2D_3D");
        }

        for(int i = 0; i < R4DX; i++){
            Room room = new Room();
            room.setName("Room " + count++);
            room.setTheatre(theatre);
            room.setType("4DX");
        }

        for(int i = 0; i < RIMAX; i++){
            Room room = new Room();
            room.setName("Room " + count++);
            room.setTheatre(theatre);
            room.setType("IMAX");
        }

        theatreRepository.save(theatre);

        HashMap<String, Object> result = new HashMap<>();
        result.put("code", "200");
        result.put("message", "Success");

        return result;
    }

    @Override
    public HashMap<String, Object> updateTheatre(MultipartFile image, HashMap<String, Object> params) throws IOException, URISyntaxException {
        if(params.get("id") == null || params.get("name") == null || params.get("address") == null || params.get("description") == null || params.get("tel") == null){
            HashMap<String, Object> result = new HashMap<>();
            result.put("code", "400");
            result.put("message", "Missing params");
            return result;
        }

        String name = params.get("name").toString();
        String address = params.get("address").toString();
        String description = params.get("description").toString();
        String tel = params.get("tel").toString();
        Integer id = Integer.parseInt(params.get("id").toString());

        Theatre theatre = theatreRepository.findById(id).get();
        theatre.setName(name);
        theatre.setAddress(address);
        theatre.setDescription(description);
        theatre.setTel(tel);

        if(image != null && !image.isEmpty()){
            String fileName = image.getOriginalFilename();

            String[] split = fileName.split("\\.");
            String fileExtension = split[split.length - 1];
            if(!fileExtension.equals("jpg") && !fileExtension.equals("png") && !fileExtension.equals("jpeg") && !fileExtension.equals("gif") && !fileExtension.equals("bmp") && !fileExtension.equals("svg") && !fileExtension.equals("webp")){
                HashMap<String, Object> result = new HashMap<>();
                result.put("code", "400");
                result.put("message", "Wrong file extension");
                return result;
            }

            ClassLoader classLoader = getClass().getClassLoader();
            URI uri = classLoader.getResource("static/images/MovieTheatres").toURI();
            Path imagePath = Paths.get(uri).resolve(fileName);
            Files.copy(image.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);

            theatre.setImage("/images/MovieTheatres/" + fileName);
        }

        theatreRepository.save(theatre);

        HashMap<String, Object> result = new HashMap<>();
        result.put("code", "200");
        result.put("message", "Success");

        return result;
    }

    @Override
    public HashMap<String, Object> deleteTheatre(Integer id) {
        if(theatreRepository.getTheatreById(id).size() == 0){
            HashMap<String, Object> result = new HashMap<>();
            result.put("code", "400");
            result.put("message", "Theatre not found");
            return result;
        }

        theatreRepository.deleteById(id);

        HashMap<String, Object> result = new HashMap<>();
        result.put("code", "200");
        result.put("message", "Success");

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

    @Override
    public HashMap<String, Object> addMovie(MultipartFile image, HashMap<String, Object> params) throws IOException, URISyntaxException {
        if(params.get("name") == null || params.get("description") == null || params.get("duration") == null || params.get("trailer") == null || params.get("releaseDate") == null || params.get("director") == null || params.get("actors") == null || params.get("status") == null || params.get("category_id") == null || params.get("age_restrict") == null){
            HashMap<String, Object> result = new HashMap<>();
            result.put("code", "400");
            result.put("message", "Missing params");
            return result;
        }

        if(image == null){
            HashMap<String, Object> result = new HashMap<>();
            result.put("code", "400");
            result.put("message", "Missing image");
            return result;
        }

        String name = params.get("name").toString();
        String description = params.get("description").toString();
        Integer duration = Integer.parseInt(params.get("duration").toString());
        String trailer = params.get("trailer").toString();
        LocalDate releaseDate = LocalDate.parse(params.get("releaseDate").toString());
        String director = params.get("director").toString();
        String actors = params.get("actors").toString();
        Integer status = Integer.parseInt(params.get("status").toString());
        String category_id = params.get("category_id").toString();
        String age_restrict = params.get("age_restrict").toString();
        String fileName = image.getOriginalFilename();

        String[] split = fileName.split("\\.");
        String fileExtension = split[split.length - 1];
        if(!fileExtension.equals("jpg") && !fileExtension.equals("png") && !fileExtension.equals("jpeg") && !fileExtension.equals("gif") && !fileExtension.equals("bmp") && !fileExtension.equals("svg") && !fileExtension.equals("webp")){
            HashMap<String, Object> result = new HashMap<>();
            result.put("code", "400");
            result.put("message", "Wrong file extension");
            return result;
        }

        ClassLoader classLoader = getClass().getClassLoader();
        URI uri = classLoader.getResource("static/images/Movie").toURI();
        Path imagePath = Paths.get(uri).resolve(fileName);
        Files.copy(image.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);

        Movie movie = new Movie();
        movie.setName(name);
        movie.setDescription(description);
        movie.setDuration(duration);
        movie.setTrailer(trailer);
        movie.setReleaseDate(releaseDate);
        movie.setDirector(director);
        movie.setActors(actors);
        movie.setStatus(status);
        movie.setAgeRestrict(age_restrict);
        movie.setImage("/images/Movie/" + fileName);


        String[] splitCategory = category_id.split(",");
        List<Category> categoryList = new ArrayList<>();
        for(String id : splitCategory){
            categoryList.add(categoryRepository.findCategoryById(Integer.parseInt(id)));
        }
        movie.setCategoryList(categoryList);

        movieRepository.save(movie);

        HashMap<String, Object> result = new HashMap<>();
        result.put("code", "200");
        result.put("message", "Success");

        return result;
    }

    @Override
    public HashMap<String, Object> updateMovie(MultipartFile image, HashMap<String, Object> params) throws IOException, URISyntaxException {
        if(params.get("id") == null || params.get("name") == null || params.get("duration") == null || params.get("description") == null || params.get("releaseDate") == null || params.get("status") == null || params.get("director") == null || params.get("actors") == null || params.get("trailer") == null  || params.get("age_restrict") == null || params.get("category_id") == null){
            HashMap<String, Object> result = new HashMap<>();
            result.put("code", "400");
            result.put("message", "Missing params");
            return result;
        }

        Integer id = Integer.parseInt(params.get("id").toString());
        String name = params.get("name").toString();
        Integer duration = Integer.parseInt(params.get("duration").toString());
        String description = params.get("description").toString();
        String releaseDate = params.get("releaseDate").toString();
        Integer status = Integer.parseInt(params.get("status").toString());
        String director = params.get("director").toString();
        String actors = params.get("actors").toString();
        String trailer = params.get("trailer").toString();
        String age_restrict = params.get("age_restrict").toString();
        String category_id = params.get("category_id").toString();

        if(movieRepository.getMovieById(id).size() == 0){
            HashMap<String, Object> result = new HashMap<>();
            result.put("code", "400");
            result.put("message", "Movie not found");
            return result;
        }

        Movie movie = movieRepository.findMovieById(id);
        movie.setName(name);
        movie.setDuration(duration);
        movie.setDescription(description);
        movie.setReleaseDate(LocalDate.parse(releaseDate));
        movie.setStatus(status);
        movie.setDirector(director);
        movie.setActors(actors);
        movie.setTrailer(trailer);
        movie.setAgeRestrict(age_restrict);

        movie.getCategoryList().removeAll(movie.getCategoryList());
        String[] categoryList = category_id.split(",");
        for (String category : categoryList) {
            movie.getCategoryList().add(categoryRepository.findCategoryById(Integer.parseInt(category)));
        }

        if(image != null){
            String fileName = image.getOriginalFilename();

            String[] split = fileName.split("\\.");
            String fileExtension = split[split.length - 1];
            if(!fileExtension.equals("jpg") && !fileExtension.equals("png") && !fileExtension.equals("jpeg") && !fileExtension.equals("gif") && !fileExtension.equals("bmp") && !fileExtension.equals("svg") && !fileExtension.equals("webp")){
                HashMap<String, Object> result = new HashMap<>();
                result.put("code", "400");
                result.put("message", "Wrong file extension");
                return result;
            }

            ClassLoader classLoader = getClass().getClassLoader();
            URI uri = classLoader.getResource("static/images/Movie").toURI();
            Path imagePath = Paths.get(uri).resolve(fileName);
            Files.copy(image.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);

            movie.setImage("/images/Movie/" + fileName);
        }

        movieRepository.save(movie);

        HashMap<String, Object> result = new HashMap<>();
        result.put("code", "200");
        result.put("message", "Success");
        return result;
    }

    @Override
    public HashMap<String, Object> deleteMovie(Integer id) {
        if(movieRepository.getMovieById(id).size() == 0){
            HashMap<String, Object> result = new HashMap<>();
            result.put("code", "400");
            result.put("message", "Movie not found");
            return result;
        }

        movieRepository.deleteById(id);

        HashMap<String, Object> result = new HashMap<>();
        result.put("code", "200");
        result.put("message", "Success");
        return result;
    }

    @Override
    public HashMap<String, Object> addFoodCombo(MultipartFile image, HashMap<String, Object> params) throws IOException, URISyntaxException {
        if(params.get("name") == null || params.get("description") == null || params.get("price") == null || params.get("popcorn") == null || params.get("drink") == null){
            HashMap<String, Object> result = new HashMap<>();
            result.put("code", "400");
            result.put("message", "Missing params");
            return result;
        }

        if(image == null){
            HashMap<String, Object> result = new HashMap<>();
            result.put("code", "400");
            result.put("message", "Missing image");
            return result;
        }

        String name = params.get("name").toString();
        String description = params.get("description").toString();
        Integer price = Integer.parseInt(params.get("price").toString());
        Integer popcorn = Integer.parseInt(params.get("popcorn").toString());
        Integer drink = Integer.parseInt(params.get("drink").toString());
        String fileName = image.getOriginalFilename();

        String[] split = fileName.split("\\.");
        String fileExtension = split[split.length - 1];
        if(!fileExtension.equals("jpg") && !fileExtension.equals("png") && !fileExtension.equals("jpeg") && !fileExtension.equals("gif") && !fileExtension.equals("bmp") && !fileExtension.equals("svg") && !fileExtension.equals("webp")){
            HashMap<String, Object> result = new HashMap<>();
            result.put("code", "400");
            result.put("message", "Wrong file extension");
            return result;
        }

        ClassLoader classLoader = getClass().getClassLoader();
        URI uri = classLoader.getResource("static/images/FoodCombo").toURI();
        Path imagePath = Paths.get(uri).resolve(fileName);
        Files.copy(image.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);

        FoodCombo foodCombo = new FoodCombo();
        foodCombo.setName(name);
        foodCombo.setDescription(description);
        foodCombo.setPrice(price);
        foodCombo.setPopcorn(popcorn);
        foodCombo.setDrink(drink);
        foodCombo.setImage("/images/FoodCombo/" + fileName);

        foodComboRepository.save(foodCombo);

        HashMap<String, Object> result = new HashMap<>();
        result.put("code", "200");
        result.put("message", "Success");

        return result;
    }

    @Override
    public HashMap<String, Object> updateFoodCombo(MultipartFile image, HashMap<String, Object> params) throws IOException, URISyntaxException {
        if(params.get("id") == null || params.get("name") == null || params.get("description") == null || params.get("price") == null || params.get("popcorn") == null || params.get("drink") == null){
            HashMap<String, Object> result = new HashMap<>();
            result.put("code", "400");
            result.put("message", "Missing params");
            return result;
        }

        Integer id = Integer.parseInt(params.get("id").toString());
        FoodCombo foodCombo = foodComboRepository.findFoodComboById(id);
        if(foodCombo == null){
            HashMap<String, Object> result = new HashMap<>();
            result.put("code", "400");
            result.put("message", "Food combo not found");
            return result;
        }

        if(image != null && !image.isEmpty()){
            String fileName = image.getOriginalFilename();

            String[] split = fileName.split("\\.");
            String fileExtension = split[split.length - 1];
            if(!fileExtension.equals("jpg") && !fileExtension.equals("png") && !fileExtension.equals("jpeg") && !fileExtension.equals("gif") && !fileExtension.equals("bmp") && !fileExtension.equals("svg") && !fileExtension.equals("webp")){
                HashMap<String, Object> result = new HashMap<>();
                result.put("code", "400");
                result.put("message", "Wrong file extension");
                return result;
            }

            ClassLoader classLoader = getClass().getClassLoader();
            URI uri = classLoader.getResource("static/images/FoodCombo").toURI();
            Path imagePath = Paths.get(uri).resolve(fileName);
            Files.copy(image.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
            foodCombo.setImage("/images/FoodCombo/" + fileName);
        }

        foodCombo.setName(params.get("name").toString());
        foodCombo.setDescription(params.get("description").toString());
        foodCombo.setPrice(Integer.parseInt(params.get("price").toString()));
        foodCombo.setPopcorn(Integer.parseInt(params.get("popcorn").toString()));
        foodCombo.setDrink(Integer.parseInt(params.get("drink").toString()));

        foodComboRepository.save(foodCombo);

        HashMap<String, Object> result = new HashMap<>();
        result.put("code", "200");
        result.put("message", "Success");

        return result;
    }

    @Override
    public HashMap<String, Object> deleteFoodCombo(Integer id) {
        FoodCombo foodCombo = foodComboRepository.findFoodComboById(id);
        if(foodCombo == null){
            HashMap<String, Object> result = new HashMap<>();
            result.put("code", "400");
            result.put("message", "Food combo not found");
            return result;
        }

        foodComboRepository.delete(foodCombo);

        HashMap<String, Object> result = new HashMap<>();
        result.put("code", "200");
        result.put("message", "Success");

        return result;
    }
}
