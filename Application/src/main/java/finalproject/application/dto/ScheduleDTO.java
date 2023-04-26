package finalproject.application.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class ScheduleDTO {

    public static ScheduleDTO getInstance() {
        return new ScheduleDTO();
    }

    @JsonProperty("schedule_id")
    private Integer scheduleId;

    @JsonProperty("movie_id")
    private Integer movieId;

    @JsonProperty("movie_name")
    private String movieName;

    @JsonProperty("room_id")
    private Integer roomId;

    @JsonProperty("theatre_id")
    private Integer theatreId;

    @JsonProperty("date")
    private String date;

    @JsonProperty("price")
    private Integer price;

    @JsonProperty("start_times")
    private String startTimes;

    @JsonProperty("end_times")
    private String endTimes;

    @JsonProperty("schedule_time_ids")
    private String scheduleTimeIds;

    @JsonProperty("theatre_name")
    private String theatreName;

    @JsonProperty("theatre_address")
    private String theatreAddress;

    @JsonProperty("theatre_image")
    private String theatreImage;

    @JsonProperty("room_name")
    private String roomName;

    @JsonProperty("room_type")
    private String roomType;

    @JsonProperty("room_capacity")
    private Integer roomCapacity;

    public ScheduleDTO convertToObject(Object[] params) throws ParseException {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setScheduleId((Integer) params[0]);
        scheduleDTO.setMovieId((Integer) params[1]);
        scheduleDTO.setMovieName((String) params[2]);
        scheduleDTO.setRoomId((Integer) params[3]);
        scheduleDTO.setTheatreId((Integer) params[4]);
        scheduleDTO.setDate(((Timestamp) params[5]).toString());
        scheduleDTO.setPrice((Integer) params[6]);
        scheduleDTO.setStartTimes((String) params[7]);
        scheduleDTO.setEndTimes((String) params[8]);
        scheduleDTO.setScheduleTimeIds((String) params[9]);
        scheduleDTO.setTheatreName((String) params[10]);
        scheduleDTO.setTheatreAddress((String) params[11]);
        scheduleDTO.setTheatreImage((String) params[12]);
        scheduleDTO.setRoomName((String) params[13]);
        scheduleDTO.setRoomType((String) params[14]);
        scheduleDTO.setRoomCapacity((Integer) params[15]);
        return scheduleDTO;
    }

}