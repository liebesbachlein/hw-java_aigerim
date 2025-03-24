package app.space.entity;

import java.util.Objects;

public class Entity {
    private int id;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return id == entity.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
