package finalproject.application.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "food_combo_ticket")
@NoArgsConstructor @AllArgsConstructor @Getter @Setter @ToString
public class FoodComboTicket {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_combo_id", referencedColumnName = "id")
    private FoodCombo foodCombo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id", referencedColumnName = "id")
    private Ticket ticket;

    @Column(name = "quantity")
    private Integer quantity;
}
