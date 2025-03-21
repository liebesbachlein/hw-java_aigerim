package app.space.repo;
import java.util.Map;

public interface RepoSource<T> {
     Map<Integer, T> getEntityMap();

     void close();
}
