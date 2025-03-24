package app.space.repo;
import app.space.entity.Entity;

import java.util.Map;

public interface DataSource<T extends Entity> {
     Map<Integer, T> getEntityMap();
     void close();
}
