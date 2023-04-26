package finalproject.application.repository;

import finalproject.application.entity.ScheduleTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleTimeRepository extends JpaRepository<ScheduleTime, Integer> {
    ScheduleTime getScheduleTimeById(int id);
}
