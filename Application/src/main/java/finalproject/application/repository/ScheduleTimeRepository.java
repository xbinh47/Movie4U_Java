package finalproject.application.repository;

import finalproject.application.entity.ScheduleTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleTimeRepository extends JpaRepository<ScheduleTime, Integer> {
    ScheduleTime getScheduleTimeById(int id);

    @Query(value = "SELECT COUNT(*) FROM schedule_time WHERE ((start_time >= ?1 AND start_time <= ?2) OR (end_time >= ?1 AND end_time <= ?2)) AND schedule_id IN (SELECT id FROM schedule WHERE date = ?3 AND room_id = ?4)", nativeQuery = true)
    Integer countScheduleTimeByStartTimeAndEndTimeAndDateAndRoom(String startTime, String endTime, String date, Integer roomId);
}
