package space.entity;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "spaces")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Space {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private int price;

    public Space(String name, int price) {
        this.name = name;
        this.price = price;
    }
}



