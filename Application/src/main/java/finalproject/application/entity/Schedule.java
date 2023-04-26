package finalproject.application.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "schedule")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Schedule {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime date;

    @Column(name = "price")
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", referencedColumnName = "id")
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theatre_id", referencedColumnName = "id")
    private Theatre theatre;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("schedule")
    private List<ScheduleTime> scheduleTimes;
}
