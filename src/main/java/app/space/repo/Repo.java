package app.space.repo;

import app.space.entity.Entity;
import app.space.util.DuplicateIdException;
import app.space.util.matcher.CriteriaMatcher;
import java.util.List;
import java.util.Optional;


public interface Repo<T extends Entity>  {
     Optional<T> findById(int id);

     List<T> getAll();

     T save(T item) throws DuplicateIdException;

     boolean delete(int id);

     List<T> findByCriteria(CriteriaMatcher<T> matcher);
}
