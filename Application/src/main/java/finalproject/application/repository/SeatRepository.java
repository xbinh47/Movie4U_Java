package finalproject.application.repository;

import finalproject.application.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer> {
    Seat getSeatByName(String name);
}
