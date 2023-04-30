package finalproject.application.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.HashMap;

public interface AdminService {
    HashMap<String,Object> getAllSchedule() throws ParseException;
    HashMap<String,Object> getAllTheatres();
    HashMap<String,Object> getTheatreById(Integer id);
    HashMap<String, Object> addTheatre(MultipartFile image, HashMap<String, Object> params) throws IOException, URISyntaxException;
    HashMap<String, Object> updateTheatre(MultipartFile image, HashMap<String, Object> params) throws IOException, URISyntaxException;
    HashMap<String,Object> deleteTheatre(Integer id);
    HashMap<String,Object> getAllUser();
    HashMap<String,Object> getRevenue();
    HashMap<String,Object> addMovie(MultipartFile image, HashMap<String, Object> params) throws IOException, URISyntaxException;
    HashMap<String,Object> updateMovie(MultipartFile image, HashMap<String, Object> params) throws IOException, URISyntaxException;
    HashMap<String,Object> deleteMovie(Integer id);
    HashMap<String,Object> addFoodCombo(MultipartFile image ,HashMap<String, Object> params) throws IOException, URISyntaxException;
    HashMap<String,Object> updateFoodCombo(MultipartFile image ,HashMap<String, Object> params) throws IOException, URISyntaxException;
    HashMap<String,Object> deleteFoodCombo(Integer id);
    HashMap<String,Object> addSchedule(HashMap<String, Object> params);
}
