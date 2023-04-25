package finalproject.application.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "food_combo")
public class FoodCombo {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "drink", nullable = false)
    private String drink;

    @Column(name = "popcorn", nullable = false)
    private String popcorn;

    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "price", nullable = false)
    private Long price;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToMany
    @JoinTable(name = "food_combo_ticket",
            joinColumns = @JoinColumn(name = "food_combo_id"),
            inverseJoinColumns = @JoinColumn(name = "ticket_id"))
    private List<Ticket> ticketList;
}
