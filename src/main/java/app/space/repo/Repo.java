package app.space.repo;

import app.space.util.DuplicateIdException;
import app.space.util.matcher.CriteriaMatcher;

import java.util.List;

public interface Repo<T> {
     T findById(int id);

     List<T> getAll();

     T save(T item) throws DuplicateIdException;

     boolean delete(int id);

     List<T> findByCriteria(CriteriaMatcher<T> matcher);

     int count();
}
