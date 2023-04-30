package finalproject.application.repository;

import finalproject.application.entity.Movie;
import finalproject.application.entity.Poster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
    @Query(nativeQuery = true,value = "" +
            "SELECT movie.id, movie.name, movie.duration, movie.release_date, movie.image, movie.trailer, movie.director, movie.actors, movie.age_restrict, movie.description, GROUP_CONCAT(category.id) as category_id ,GROUP_CONCAT(category.name) AS categories, movie.status\n" +
            "                    FROM movie\n" +
            "                    INNER JOIN movie_category ON movie.id = movie_category.movie_id\n" +
            "                    INNER JOIN category ON movie_category.category_id = category.id\n" +
            "                    GROUP BY movie.id")
    List<Object[]> getAllMovie();
    @Query(nativeQuery = true,value = "" +
            "SELECT movie.id, movie.name, movie.duration, movie.release_date, movie.image, movie.trailer, movie.director, movie.actors, movie.age_restrict, movie.description, GROUP_CONCAT(category.id) as category_id ,GROUP_CONCAT(category.name) AS categories, movie.status\n" +
            "                    FROM movie\n" +
            "                    INNER JOIN movie_category ON movie.id = movie_category.movie_id\n" +
            "                    INNER JOIN category ON movie_category.category_id = category.id\n" +
            "                    WHERE movie.id = ?1 \n" +
            "                    GROUP BY movie.id")
    List<Object[]> getMovieById(Integer id);
    @Query(nativeQuery = true,value = "" +
            "SELECT movie.id, movie.name, movie.duration, movie.release_date, movie.image, movie.trailer, movie.director, movie.actors, movie.age_restrict, movie.description, GROUP_CONCAT(category.id) as category_id ,GROUP_CONCAT(category.name) AS categories, movie.status\n" +
            "                    FROM movie\n" +
            "                    INNER JOIN movie_category ON movie.id = movie_category.movie_id\n" +
            "                    INNER JOIN category ON movie_category.category_id = category.id\n" +
            "                    WHERE movie.status = ?1 \n" +
            "                    GROUP BY movie.id")
    List<Object[]> getMovieByStatus(Integer status);
    @Query(nativeQuery = true,value = "" +
            "SELECT movie.id, movie.name, movie.duration, movie.release_date, movie.image, movie.trailer, movie.director, movie.actors, movie.age_restrict, movie.description, GROUP_CONCAT(category.id) as category_id ,GROUP_CONCAT(category.name) AS categories, movie.status\n" +
            "                    FROM movie\n" +
            "                    INNER JOIN movie_category ON movie.id = movie_category.movie_id\n" +
            "                    INNER JOIN category ON movie_category.category_id = category.id\n" +
            "                    WHERE movie.name LIKE %?1% \n" +
            "                    GROUP BY movie.id")
    List<Object[]> getMovieByName(String name);
    Movie findMovieById(Integer id);
}
