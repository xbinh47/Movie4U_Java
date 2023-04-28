package finalproject.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDate;
@NoArgsConstructor @AllArgsConstructor @Getter @Setter @ToString
public class MovieDTO {
    public static MovieDTO getInstance() {
        return new MovieDTO();
    }
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("duration")
    private Integer duration;

    @JsonProperty("release_date")
    private String releaseDate;

    @JsonProperty("image")
    private String image;

    @JsonProperty("trailer")
    private String trailer;

    @JsonProperty("director")
    private String director;

    @JsonProperty("actors")
    private String actors;

    @JsonProperty("age_restrict")
    private String ageRestrict;

    @JsonProperty("description")
    private String description;

    @JsonProperty("category_id")
    private String categoryId;

    @JsonProperty("categories")
    private String categories;

    @JsonProperty("status")
    private Integer status;

    public MovieDTO convertToObject(Object[] movie) {
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setId((Integer) movie[0]);
        movieDTO.setName((String) movie[1]);
        movieDTO.setDuration((Integer) movie[2]);
        movieDTO.setReleaseDate( movie[3].toString());
        movieDTO.setImage((String) movie[4]);
        movieDTO.setTrailer((String) movie[5]);
        movieDTO.setDirector((String) movie[6]);
        movieDTO.setActors((String) movie[7]);
        movieDTO.setAgeRestrict((String) movie[8]);
        movieDTO.setDescription((String) movie[9]);
        movieDTO.setCategoryId((String) movie[10]);
        movieDTO.setCategories((String) movie[11]);
        movieDTO.setStatus((Integer) movie[12]);
        return movieDTO;
    }

}
