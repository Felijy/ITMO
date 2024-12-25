package web.lab.web41;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "points")
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @Column(nullable = false)
    @Getter
    @Setter
    private Double x;

    @Column(nullable = false)
    @Getter
    @Setter
    private Double y;

    @Column(nullable = false)
    @Getter
    @Setter
    private Double r;

    @Column(nullable = false)
    @Getter
    @Setter
    private Boolean hit;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @Getter
    @Setter
    private User user;
}
