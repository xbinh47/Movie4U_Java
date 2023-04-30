package finalproject.application.repository;

import finalproject.application.entity.ScheduleTime;
import finalproject.application.entity.Seat;
import finalproject.application.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.*;
import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer> {
    @Query(nativeQuery = true, value = "SELECT GROUP_CONCAT(name) AS seat_names FROM seat WHERE schedule_time_id = ?1")
    Object[] getSeatByScheduleTimeId(Integer schedule_time_id);

    @Query(nativeQuery = true, value = "SELECT COUNT(*) as total FROM seat")
    Integer getTotalSeat();

    Seat getSeatByNameAndScheduleTime(String name, ScheduleTime scheduleTime);

    List<Seat> getSeatByTicket(Ticket ticket);
}
