package finalproject.application.repository;

import finalproject.application.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer> {
    @Query(nativeQuery = true, value = "SELECT GROUP_CONCAT(name) AS seat_names FROM seat WHERE schedule_time_id = ?1")
    Object[] getSeatByScheduleTimeId(Integer schedule_time_id);

    Seat getSeatByName(String name);
}
