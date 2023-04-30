package finalproject.application.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "food_combo")
@NoArgsConstructor @AllArgsConstructor @Getter @Setter @ToString
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "foodComboTicketList"})
public class FoodCombo {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "drink", nullable = false)
    private Integer drink;

    @Column(name = "popcorn", nullable = false)
    private Integer popcorn;

    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "description", nullable = false)
    private String description;

    @OneToMany(mappedBy = "foodCombo", cascade = CascadeType.ALL)
    private List<FoodComboTicket> foodComboTicketList;
}
