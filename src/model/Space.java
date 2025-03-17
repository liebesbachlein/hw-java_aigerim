package model;

import lombok.Getter;
import util.IdGenerator;

import java.io.Serializable;
import java.util.Objects;

@Getter
public class Space implements Serializable {
    public enum Type {
        OPEN,
        PRIVATE,
        ROOM
    }

    private final int id;
    private String name;
    private Type type;
    private int price;

    public Space(Type type, String name, int price) {
        this.name = name;
        this.id = IdGenerator.generateId();
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Space space = (Space) o;
        return id == space.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

