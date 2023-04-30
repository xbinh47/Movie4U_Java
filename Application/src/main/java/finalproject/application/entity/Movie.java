package finalproject.application.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "movie")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "duration", nullable = false)
    private Integer duration;

    @Column(name = "release_date", nullable = false)
    private LocalDate releaseDate;

    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "trailer", nullable = false)
    private String trailer;

    @Column(name = "status", nullable = false, columnDefinition = "INT DEFAULT 1")
    private Integer status;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "age_restrict", nullable = false)
    private String ageRestrict;

    @Column(name = "director", nullable = false)
    private String director;

    @Column(name = "actors", nullable = false)
    private String actors;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("movie")
    private List<Schedule> schedules;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "movie_category",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categoryList;
}
