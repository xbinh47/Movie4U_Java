package finalproject.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
@NoArgsConstructor @AllArgsConstructor @Getter @Setter @ToString
public class TicketDTO {
    public static TicketDTO getInstance() {
        return new TicketDTO();
    }
    @JsonProperty("ticket_id")
    private Integer ticketId;

    @JsonProperty("schedule_time_id")
    private Integer scheduleTimeId;

    @JsonProperty("account_id")
    private Integer accountId;

    @JsonProperty("theatre_id")
    private Integer theatreId;

    @JsonProperty("theatre_name")
    private String theatreName;

    @JsonProperty("room_name")
    private String roomName;

    @JsonProperty("start_time")
    private String startTime;

    @JsonProperty("end_time")
    private String endTime;

    @JsonProperty("date")
    private String date;

    @JsonProperty("price")
    private BigDecimal price;

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
    private BigDecimal total;
    public TicketDTO convertToObject(Object[] ticket) {
        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setTicketId((Integer) ticket[0]);
        ticketDTO.setScheduleTimeId((Integer) ticket[1]);
        ticketDTO.setAccountId((Integer) ticket[2]);
        ticketDTO.setTheatreId((Integer) ticket[3]);
        ticketDTO.setTheatreName((String) ticket[4]);
        ticketDTO.setRoomName((String) ticket[5]);
        ticketDTO.setStartTime((String) ticket[6]);
        ticketDTO.setEndTime((String) ticket[7]);
        ticketDTO.setDate((String) ticket[8]);
        ticketDTO.setPrice((BigDecimal) ticket[9]);
        ticketDTO.setMovieId((Integer) ticket[10]);
        ticketDTO.setMovieName((String) ticket[11]);
        ticketDTO.setMovieImage((String) ticket[12]);
        ticketDTO.setMovieDuration((Integer) ticket[13]);
        ticketDTO.setMovieDescription((String) ticket[14]);
        ticketDTO.setMovieTrailer((String) ticket[15]);
        ticketDTO.setSeatNames((String) ticket[16]);
        ticketDTO.setFoodComboIds((String) ticket[17]);
        ticketDTO.setFoodComboNames((String) ticket[18]);
        ticketDTO.setFoodComboPrices((String) ticket[19]);
        ticketDTO.setFoodComboImages((String) ticket[20]);
        ticketDTO.setTotal((BigDecimal) ticket[21]);
        return ticketDTO;
    }
}
