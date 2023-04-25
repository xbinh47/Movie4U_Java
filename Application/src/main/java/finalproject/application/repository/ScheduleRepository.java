package finalproject.application.repository;

import finalproject.application.dto.ScheduleDTO;
import finalproject.application.entity.Schedule;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    @Query(nativeQuery = true, value = "SELECT sch.id AS schedule_id, sch.movie_id, m.name AS movie_name, sch.room_id, sch.theatre_id, CONVERT_TZ(sch.date, '-07:00', '+07:00') as date, sch.price, GROUP_CONCAT(DISTINCT st.start_time) AS start_times, GROUP_CONCAT(DISTINCT st.end_time) AS end_times, GROUP_CONCAT(DISTINCT st.id) AS schedule_time_ids, th.name AS theatre_name, th.address AS theatre_address, th.image AS theatre_image, r.name AS room_name, r.type AS room_type, r.capacity AS room_capacity FROM schedule sch LEFT JOIN theatre th ON sch.theatre_id = th.id LEFT JOIN room r ON sch.room_id = r.id LEFT JOIN schedule_time st ON sch.id = st.schedule_id JOIN movie m ON sch.movie_id = m.id GROUP BY sch.id, th.name, th.address, th.image, r.name, r.type, r.capacity")
    List<Object[]> getAllSchedule();
}
