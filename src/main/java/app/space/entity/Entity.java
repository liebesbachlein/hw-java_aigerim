package app.space.entity;

import app.space.util.IdGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

public class Entity implements Serializable {
    private int id;
    public Entity() {
        id = IdGenerator.generateId();
    }
    public Entity(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return id == entity.id;
    }
    public int hashCode() {
        return Objects.hash(id);
    }
}
