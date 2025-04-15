package space.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "reservation")
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    // Owning side
    @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    User owner;

    // Owning side
    @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(name = "space_id", nullable = false)
    Space space;

    @Column(nullable = false)
    LocalDate date;

    @Column(nullable = false)
    LocalTime hour;
}
