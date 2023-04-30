package finalproject.application.repository;

import finalproject.application.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    Room findRoomById(Integer id);
}
