package finalproject.application.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "theatre")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Theatre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "tel", nullable = false)
    private String tel;

    @Column(name = "description", nullable = false)
    private String description;

    @OneToMany(mappedBy = "theatre", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("theatre")
    private List<Room> rooms;

    @OneToMany(mappedBy = "theatre", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("theatre")
    private List<Schedule> schedules;


}
