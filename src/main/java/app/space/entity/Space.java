package app.space.entity;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Setter
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

    public Space(Type type, String name, int price) {
        super();
        this.name = name;
        this.type = type;
        this.price = price;
    }

    public Space(int id, Type type, String name, int price) {
        super(id);
        this.name = name;
        this.type = type;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Space{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", price=" + price +
                '}';
    }
}

