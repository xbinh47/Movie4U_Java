package finalproject.application.service;

import java.lang.invoke.StringConcatException;
import java.util.HashMap;

public interface MovieService {
    HashMap<String,Object> getAllMovice();
    HashMap<String,Object> getMoiveById();
    HashMap<String,Object> getMovieByName();
    HashMap<String,Object> getAllPoster();
}
