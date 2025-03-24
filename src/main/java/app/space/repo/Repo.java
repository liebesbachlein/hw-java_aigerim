package app.space.repo;

import app.space.entity.Entity;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


public interface Repo<T extends Entity>  {
     Optional<T> findById(int id);

     List<T> getAll();

     Optional<T> save(T item);

     boolean delete(int id);
}
