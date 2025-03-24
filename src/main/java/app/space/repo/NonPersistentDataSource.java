package app.space.repo;
import app.space.entity.Entity;

import java.util.HashMap;
import java.util.Map;

public class NonPersistentDataSource<T extends Entity> implements DataSource<T> {
    private final Map<Integer, T> entityMap;

    public NonPersistentDataSource() {
        entityMap = new HashMap<>();
    }

    public Map<Integer, T> getEntityMap() {
        return entityMap;
    }

    public void close() {};
}
