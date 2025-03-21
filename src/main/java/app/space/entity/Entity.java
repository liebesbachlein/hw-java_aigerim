package app.space.entity;

import app.space.util.IdGenerator;
import lombok.Getter;
import java.util.Objects;

@Getter
public class Entity {
    int id;

    public Entity() {
        id = IdGenerator.generateId();
    }

    public Entity(int id) {
        this.id = id;
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
