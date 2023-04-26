package finalproject.application.repository;

import finalproject.application.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
    Movie getMovieById(int id);
}
