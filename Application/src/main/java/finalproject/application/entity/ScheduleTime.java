package finalproject.application.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "schedule_time")
@NoArgsConstructor @AllArgsConstructor @Getter @Setter @ToString
public class ScheduleTime {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "start_time")
    private String startTime;
    @Column(name = "end_time")
    private String endTime;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", referencedColumnName = "id")
    private Schedule schedule;

}
