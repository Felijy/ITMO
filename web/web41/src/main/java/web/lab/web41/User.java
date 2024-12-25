package web.lab.web41;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @Column(nullable = false, unique = true)
    @Getter
    @Setter
    private String username;

    @Column(nullable = false)
    @Getter
    @Setter
    private String password;
}