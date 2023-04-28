package finalproject.application.service;

import java.lang.invoke.StringConcatException;
import java.util.HashMap;

public interface MovieService {
    HashMap<String,Object> getAllMovie();
    HashMap<String,Object> getMovieById(Integer id);
    HashMap<String,Object> getMovieByName(String name);
    HashMap<String,Object> getAllPoster();
    HashMap<String,Object> getMovieByStatus(Integer status);
}
