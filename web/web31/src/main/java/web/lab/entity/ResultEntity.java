package web.lab.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "lab3", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence-generator")
    @SequenceGenerator(name = "sequence-generator", sequenceName = "lab3_id_seq", allocationSize = 1)
    private long id;

    @Column(nullable = false)
    private double x;

    @Column(nullable = false)
    private double y;

    @Column(nullable = false)
    private double r;

    @Column(nullable = false)
    private boolean hit;

    @Column(nullable = false)
    private LocalDateTime timestamp;
}
