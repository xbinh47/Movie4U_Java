package finalproject.application.repository;

import finalproject.application.entity.Poster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PosterRepository extends JpaRepository<Poster, Integer>{
    List<Poster> findAll();
}
