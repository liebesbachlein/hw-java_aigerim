package repo;

import util.DuplicateIdException;

import java.util.List;

public interface Repo<T> {
    public T findById(int id);

    public List<T> getAll();

    public T save(T item) throws DuplicateIdException;

    public boolean delete(int id);
}
