package finalproject.application.repository;

import finalproject.application.entity.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TheatreRepository extends JpaRepository<Theatre, Integer> {
    @Query(nativeQuery = true, value = "" +
            "                SELECT t.id AS theatre_id, t.name AS theatre_name, t.address AS theatre_address, t.image AS theatre_image, t.tel, t.description,\n" +
            "                COALESCE(SUM(CASE WHEN r.type = '2D/3D' THEN 1 ELSE 0 END), 0) AS 'R2D_3D',\n" +
            "                COALESCE(SUM(CASE WHEN r.type = '4DX' THEN 1 ELSE 0 END), 0) AS 'R4DX',\n" +
            "                COALESCE(SUM(CASE WHEN r.type = 'IMAX' THEN 1 ELSE 0 END), 0) AS 'RIMAX',\n" +
            "                GROUP_CONCAT(r.id) AS room_id, GROUP_CONCAT(r.name) AS room_name, GROUP_CONCAT(r.type) AS room_type\n" +
            "                FROM theatre t\n" +
            "                LEFT JOIN room r ON t.id = r.theatre_id\n" +
            "                GROUP BY t.id, t.name;")
    List<Object[]> getAllTheatre();

    @Query(nativeQuery = true, value = "" +
            "                SELECT t.id AS theatre_id, t.name AS theatre_name, t.address AS theatre_address, t.image AS theatre_image, t.tel, t.description,\n" +
            "                COALESCE(SUM(CASE WHEN r.type = '2D/3D' THEN 1 ELSE 0 END), 0) AS 'R2D_3D',\n" +
            "                COALESCE(SUM(CASE WHEN r.type = '4DX' THEN 1 ELSE 0 END), 0) AS 'R4DX',\n" +
            "                COALESCE(SUM(CASE WHEN r.type = 'IMAX' THEN 1 ELSE 0 END), 0) AS 'RIMAX',\n" +
            "                GROUP_CONCAT(r.id) AS room_id, GROUP_CONCAT(r.name) AS room_name, GROUP_CONCAT(r.type) AS room_type\n" +
            "                FROM theatre t \n" +
            "                LEFT JOIN room r ON t.id = r.theatre_id\n" +
            "                WHERE t.id = ?1 \n" +
            "                GROUP BY t.id, t.name;")
    List<Object[]> getTheatreById(Integer id);

    Theatre findTheatreById(Integer theatreId);
}
