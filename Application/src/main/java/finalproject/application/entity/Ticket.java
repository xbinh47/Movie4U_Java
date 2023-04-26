package finalproject.application.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ticket")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_time_id", referencedColumnName = "id")
    private ScheduleTime scheduleTime;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
    private List<Seat> seatList;

    @Column(name = "total")
    private Integer total;

    @Column(name = "createat", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createat;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
    private List<FoodComboTicket> foodComboTicketList;
}

