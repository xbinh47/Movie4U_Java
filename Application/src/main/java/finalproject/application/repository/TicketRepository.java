package finalproject.application.repository;

import finalproject.application.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    @Query(nativeQuery = true, value = "SELECT t.account_id, a.email, th.id AS theatre_id, th.name AS theatre_name, r.name AS room_name, t.id AS ticket_id, t.schedule_time_id, t.createAt, st.start_time, st.end_time, CONVERT_TZ(sch.date, '-07:00', '+07:00') as date, sch.price, sch.movie_id, m.name AS movie_name, m.image AS movie_image, m.duration AS movie_duration, m.description AS movie_description, m.trailer AS movie_trailer, IFNULL(GROUP_CONCAT(DISTINCT s.name), '') AS seat_names, IFNULL(GROUP_CONCAT(DISTINCT f.id), '') AS food_combo_ids, IFNULL(GROUP_CONCAT(DISTINCT f.name), '') AS food_combo_names, IFNULL(GROUP_CONCAT(DISTINCT f.price), '') AS food_combo_prices, IFNULL(GROUP_CONCAT(DISTINCT f.image), '') AS food_combo_images, t.total FROM ticket t JOIN schedule_time st ON t.schedule_time_id = st.id JOIN schedule sch ON st.schedule_id = sch.id JOIN movie m ON sch.movie_id = m.id JOIN seat s ON t.id = s.ticket_id LEFT JOIN food_combo_ticket ft ON t.id = ft.ticket_id LEFT JOIN food_combo f ON ft.food_combo_id = f.id JOIN account a ON t.account_id = a.id JOIN theatre th ON sch.theatre_id = th.id JOIN room r ON sch.room_id = r.id WHERE t.id = ?1 GROUP BY t.id, st.start_time, st.end_time, sch.date, sch.price, sch.movie_id, m.name, m.image, m.duration, m.description, m.trailer ORDER BY t.createAt DESC")
    List<Object[]> getTicketDetails(Integer ticketId);

    @Query(nativeQuery = true, value = "SELECT t.account_id, a.email, th.id AS theatre_id, th.name AS theatre_name, r.name AS room_name, t.id AS ticket_id, t.schedule_time_id, t.createAt, st.start_time, st.end_time, CONVERT_TZ(sch.date, '-07:00', '+07:00') as date, sch.price, sch.movie_id, m.name AS movie_name, m.image AS movie_image, m.duration AS movie_duration, m.description AS movie_description, m.trailer AS movie_trailer, IFNULL(GROUP_CONCAT(DISTINCT s.name), '') AS seat_names, IFNULL(GROUP_CONCAT(DISTINCT f.id), '') AS food_combo_ids, IFNULL(GROUP_CONCAT(DISTINCT f.name), '') AS food_combo_names, IFNULL(GROUP_CONCAT(DISTINCT f.price), '') AS food_combo_prices, IFNULL(GROUP_CONCAT(DISTINCT f.image), '') AS food_combo_images, t.total FROM ticket t JOIN schedule_time st ON t.schedule_time_id = st.id JOIN schedule sch ON st.schedule_id = sch.id JOIN movie m ON sch.movie_id = m.id JOIN seat s ON t.id = s.ticket_id LEFT JOIN food_combo_ticket ft ON t.id = ft.ticket_id LEFT JOIN food_combo f ON ft.food_combo_id = f.id JOIN account a ON t.account_id = a.id JOIN theatre th ON sch.theatre_id = th.id JOIN room r ON sch.room_id = r.id GROUP BY t.id,st.start_time,st.end_time,sch.date,sch.price,sch.movie_id,m.name,m.image,m.duration,m.description,m.trailer ORDER BY t.createAt DESC;")
    List<Object[]> getAllTicket();

    @Query(nativeQuery = true, value = "SELECT t.account_id, a.email, th.id AS theatre_id, th.name AS theatre_name, r.name AS room_name, t.id AS ticket_id, t.schedule_time_id, t.createAt, st.start_time, st.end_time, CONVERT_TZ(sch.date, '-07:00', '+07:00') as date, sch.price, sch.movie_id, m.name AS movie_name, m.image AS movie_image, m.duration AS movie_duration, m.description AS movie_description, m.trailer AS movie_trailer, IFNULL(GROUP_CONCAT(DISTINCT s.name), '') AS seat_names, IFNULL(GROUP_CONCAT(DISTINCT f.id), '') AS food_combo_ids, IFNULL(GROUP_CONCAT(DISTINCT f.name), '') AS food_combo_names, IFNULL(GROUP_CONCAT(DISTINCT f.price), '') AS food_combo_prices, IFNULL(GROUP_CONCAT(DISTINCT f.image), '') AS food_combo_images, t.total FROM ticket t JOIN schedule_time st ON t.schedule_time_id = st.id JOIN schedule sch ON st.schedule_id = sch.id JOIN movie m ON sch.movie_id = m.id JOIN seat s ON t.id = s.ticket_id LEFT JOIN food_combo_ticket ft ON t.id = ft.ticket_id LEFT JOIN food_combo f ON ft.food_combo_id = f.id JOIN account a ON t.account_id = a.id JOIN theatre th ON sch.theatre_id = th.id JOIN room r ON sch.room_id = r.id WHERE t.account_id = ?1 GROUP BY t.id, st.start_time, st.end_time, sch.date, sch.price, sch.movie_id, m.name, m.image, m.duration, m.description, m.trailer ORDER BY t.createAt DESC")
    List<Object[]> getTicketByAccountId(Integer accountId);

    @Transactional
    void deleteById(Long id);

    @Transactional
    Ticket save(Ticket ticket);
}
