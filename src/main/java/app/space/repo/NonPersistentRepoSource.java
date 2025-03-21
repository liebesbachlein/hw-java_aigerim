package app.space.repo;
import java.util.HashMap;
import java.util.Map;

public class NonPersistentRepoSource<T> implements RepoSource<T> {
    private final Map<Integer, T> entityMap;

    public NonPersistentRepoSource() {
        entityMap = new HashMap<>();
    }

    public Map<Integer, T> getEntityMap() {
        return entityMap;
    }

    public void close() {};
}
