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
    @Query(nativeQuery = true, value = "SELECT\n" +
            "        sch.id AS schedule_id,\n" +
            "        sch.movie_id,\n" +
            "        m.name AS movie_name,\n" +
            "        sch.room_id,\n" +
            "        sch.theatre_id,\n" +
            "        CONVERT_TZ(sch.date, '-07:00', '+07:00') as date,\n" +
            "        sch.price,\n" +
            "        GROUP_CONCAT(DISTINCT st.start_time) AS start_times,\n" +
            "        GROUP_CONCAT(DISTINCT st.end_time) AS end_times,\n" +
            "        GROUP_CONCAT(DISTINCT st.id) AS schedule_time_ids,\n" +
            "        th.name AS theatre_name,\n" +
            "        th.address AS theatre_address,\n" +
            "        th.image AS theatre_image,\n" +
            "        r.name AS room_name,\n" +
            "        r.type AS room_type,\n" +
            "        r.capacity AS room_capacity\n" +
            "        FROM \n" +
            "            schedule sch\n" +
            "        JOIN \n" +
            "            theatre th ON sch.theatre_id = th.id\n" +
            "        JOIN \n" +
            "            room r ON sch.room_id = r.id\n" +
            "        JOIN \n" +
            "            schedule_time st ON sch.id = st.schedule_id\n" +
            "        JOIN\n" +
            "            movie m ON sch.movie_id = m.id\n" +
            "        WHERE \n" +
            "            sch.movie_id = ?1 \n" +
            "            AND DATE(sch.date) = ?2 \n" +
            "        GROUP BY sch.id, th.name, th.address, th.image, r.name, r.type, r.capacity; ")
    List<Object[]> getScheduleByMovieIdAndDate(int movieId, String date);

    Schedule getScheduleById(int scheduleId);

}
