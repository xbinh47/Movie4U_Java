package finalproject.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter @ToString
public class TheatreDTO {

    public static TheatreDTO getInstance() {
        return new TheatreDTO();
    }

    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("address")
    private String address;

    @JsonProperty("image")
    private String image;

    @JsonProperty("tel")
    private String phone;

    @JsonProperty("description")
    private String description;

    @JsonProperty("R2D_3D")
    private Integer is2D3D;

    @JsonProperty("R4DX")
    private Integer is4DX;

    @JsonProperty("RIMAX")
    private Integer isIMAX;

    @JsonProperty("room_id")
    private String roomIds;

    @JsonProperty("room_name")
    private String roomNames;

    @JsonProperty("room_type")
    private String roomTypes;

    public TheatreDTO convertToObject(Object[] theatre) {
        TheatreDTO theatreDTO = new TheatreDTO();
        theatreDTO.setId((Integer) theatre[0]);
        theatreDTO.setName((String) theatre[1]);
        theatreDTO.setAddress((String) theatre[2]);
        theatreDTO.setImage((String) theatre[3]);
        theatreDTO.setPhone((String) theatre[4]);
        theatreDTO.setDescription((String) theatre[5]);
        theatreDTO.setIs2D3D(((BigDecimal) theatre[6]).intValue());
        theatreDTO.setIs4DX(((BigDecimal) theatre[7]).intValue());
        theatreDTO.setIsIMAX(((BigDecimal) theatre[8]).intValue());
        theatreDTO.setRoomIds((String) theatre[9]);
        theatreDTO.setRoomNames((String) theatre[10]);
        theatreDTO.setRoomTypes((String) theatre[11]);
        return theatreDTO;
    }
}
