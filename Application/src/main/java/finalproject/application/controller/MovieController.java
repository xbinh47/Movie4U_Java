package finalproject.application.controller;

import finalproject.application.dto.MovieDTO;
import finalproject.application.service.AdminService;
import finalproject.application.service.MovieService;
import finalproject.application.serviceImpl.MovieServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
@RequestMapping("/movie")
public class MovieController {
    @Autowired
    private MovieService movieService;
    @Autowired
    private AdminService adminService;
    @GetMapping("/getAllMovies")
    @ResponseBody
    public HashMap<String, Object> getAllMovies(@Param("status") Integer status){
        if (status == null){
            return movieService.getAllMovie();
        }else{
            return movieService.getMovieByStatus(status);
        }
    }
    @GetMapping("/getAllPoster")
    @ResponseBody
    public HashMap<String, Object> getAllPoster(){
        return movieService.getAllPoster();
    }
    @GetMapping("/getMovieById")
    @ResponseBody
    public HashMap<String, Object> getMovieById(@Param("id") Integer id){
        return movieService.getMovieById(id);
    }
    @GetMapping("/getMovieByName")
    @ResponseBody
    public HashMap<String, Object> getMovieByName(@Param("name") String name){
        return movieService.getMovieByName(name);
    }
    @GetMapping("/getAllTheatres")
    @ResponseBody
    public HashMap<String, Object> getAllTheatres(){
        return adminService.getAllTheatres();
    }
}
