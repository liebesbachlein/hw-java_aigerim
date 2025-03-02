package repo;

import java.util.List;

public interface Repo<T> {
    public T findById(int id);

    public List<T> getAll();

    public void save(T item);

    public boolean delete(int id);
}
