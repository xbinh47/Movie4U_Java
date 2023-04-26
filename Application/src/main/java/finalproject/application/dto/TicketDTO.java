package finalproject.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter @ToString
public class TicketDTO {
    public static TicketDTO getInstance() {
        return new TicketDTO();
    }
    @JsonProperty("account_id")
    private Integer accountId;
    @JsonProperty("email")
    private String email;
    @JsonProperty("theatre_id")
    private Integer theatreId;
    @JsonProperty("theatre_name")
    private String theatreName;
    @JsonProperty("room_name")
    private String roomName;

    @JsonProperty("ticket_id")
    private Integer ticketId;

    @JsonProperty("schedule_time_id")
    private Integer scheduleTimeId;
    @JsonProperty("createAt")
    private String createAt;

    @JsonProperty("start_time")
    private String startTime;

    @JsonProperty("end_time")
    private String endTime;

    @JsonProperty("date")
    private String date;

    @JsonProperty("price")
    private Integer price;

    @JsonProperty("movie_id")
    private Integer movieId;

    @JsonProperty("movie_name")
    private String movieName;

    @JsonProperty("movie_image")
    private String movieImage;

    @JsonProperty("movie_duration")
    private Integer movieDuration;

    @JsonProperty("movie_description")
    private String movieDescription;

    @JsonProperty("movie_trailer")
    private String movieTrailer;

    @JsonProperty("seat_names")
    private String seatNames;

    @JsonProperty("food_combo_ids")
    private String foodComboIds;

    @JsonProperty("food_combo_names")
    private String foodComboNames;

    @JsonProperty("food_combo_prices")
    private String foodComboPrices;

    @JsonProperty("food_combo_images")
    private String foodComboImages;

    @JsonProperty("total")
    private Integer total;
    public TicketDTO convertToObject(Object[] ticket) {
        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setAccountId((Integer) ticket[0]);
        ticketDTO.setEmail((String) ticket[1]);
        ticketDTO.setTheatreId((Integer) ticket[2]);
        ticketDTO.setTheatreName((String) ticket[3]);
        ticketDTO.setRoomName((String) ticket[4]);
        ticketDTO.setTicketId((Integer) ticket[5]);
        ticketDTO.setScheduleTimeId((Integer) ticket[6]);
        ticketDTO.setCreateAt(ticket[7].toString());
        ticketDTO.setStartTime(ticket[8].toString());
        ticketDTO.setEndTime(ticket[9].toString());
        ticketDTO.setDate(ticket[10].toString());
        ticketDTO.setPrice((Integer) ticket[11]);
        ticketDTO.setMovieId((Integer) ticket[12]);
        ticketDTO.setMovieName((String) ticket[13]);
        ticketDTO.setMovieImage((String) ticket[14]);
        ticketDTO.setMovieDuration((Integer) ticket[15]);
        ticketDTO.setMovieDescription((String) ticket[16]);
        ticketDTO.setMovieTrailer((String) ticket[17]);
        ticketDTO.setSeatNames((String) ticket[18]);
        ticketDTO.setFoodComboIds((String) ticket[19]);
        ticketDTO.setFoodComboNames((String) ticket[20]);
        ticketDTO.setFoodComboPrices((String) ticket[21]);
        ticketDTO.setFoodComboImages((String) ticket[22]);
        ticketDTO.setTotal((Integer) ticket[23]);
        return ticketDTO;
    }


}
