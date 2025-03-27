package app.space.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.transaction.Transactional;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "reservations")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String ownerName;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private Space space;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private Time startHour;

    @Column(nullable = false)
    private Time endHour;

    public Reservation(String ownerName, Space space, Date date, Time startHour, Time endHour) {
        this.ownerName = ownerName;
        this.space = space;
        this.date = date;
        this.startHour = startHour;
        this.endHour = endHour;
    }
}
