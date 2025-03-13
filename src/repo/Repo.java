package repo;

import util.DuplicateIdException;
import util.matcher.CriteriaMatcher;

import java.util.List;

public interface Repo<T> {
    public T findById(int id);

    public List<T> getAll();

    public T save(T item) throws DuplicateIdException;

    public boolean delete(int id);

    public List<T> findByCriteria(CriteriaMatcher<T> matcher);

    public int count();
}
