package finalproject.application.serviceImpl;

import finalproject.application.dto.MovieDTO;
import finalproject.application.entity.Poster;
import finalproject.application.repository.MovieRepository;
import finalproject.application.repository.PosterRepository;
import finalproject.application.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private PosterRepository posterRepository;
    @Override
    public HashMap<String, Object> getAllMovie() {
        List<Object[]> movieList = movieRepository.getAllMovie();
        List<MovieDTO> movieDTOList = new ArrayList<>();
        for (Object[] movie : movieList){
            movieDTOList.add(MovieDTO.getInstance().convertToObject(movie));
        }
        HashMap<String, Object> data = new HashMap<>();
        data.put("status", 200);
        data.put("message", "Success");
        data.put("data", movieDTOList);
        return data;
    }

    @Override
    public HashMap<String, Object> getMovieById(Integer id) {
        Object[] movie = movieRepository.getMovieById(id).get(0);
        MovieDTO movieDTO = MovieDTO.getInstance().convertToObject(movie);
        List<MovieDTO> movieDTOList = new ArrayList<>();
        movieDTOList.add(movieDTO);
        HashMap<String, Object> data = new HashMap<>();
        data.put("status", 200);
        data.put("message", "Success");
        data.put("data", movieDTOList);
        return data;
    }

    @Override
    public HashMap<String, Object> getMovieByName(String name) {
        List<Object[]> movieList = movieRepository.getMovieByName(name);
        List<MovieDTO> movieDTOList = new ArrayList<>();
        for (Object[] movie : movieList){
            movieDTOList.add(MovieDTO.getInstance().convertToObject(movie));
        }
        HashMap<String, Object> data = new HashMap<>();
        data.put("status", 200);
        data.put("message", "Success");
        data.put("data", movieDTOList);
        return data;
    }

    @Override
    public HashMap<String, Object> getAllPoster() {
        List<Poster> posterList = posterRepository.findAll();
        HashMap<String, Object> data = new HashMap<>();
        data.put("status", 200);
        data.put("message", "Success");
        data.put("data", posterList);
        return data;
    }

    @Override
    public HashMap<String, Object> getMovieByStatus(Integer status) {
        List<Object[]> movieList = movieRepository.getMovieByStatus(status);
        List<MovieDTO> movieDTOList = new ArrayList<>();
        for (Object[] movie : movieList){
            movieDTOList.add(MovieDTO.getInstance().convertToObject(movie));
        }
        HashMap<String, Object> data = new HashMap<>();
        data.put("status", 200);
        data.put("message", "Success");
        data.put("data", movieDTOList);
        return data;
    }
}
