package app.space.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
public class Space extends Entity implements Serializable {
    public enum Type {
        OPEN,
        PRIVATE,
        ROOM
    }

    private String name;
    private Type type;
    private int price;

    public Space(String name, Type type, int price) {
        this.name = name;
        this.type = type;
        this.price = price;
    }

    public Space(int id, String name, Type type, int price) {
        super(id);
        this.name = name;
        this.type = type;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Space{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", price=" + price +
                '}';
    }
}



