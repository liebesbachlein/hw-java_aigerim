package space.repository;

import org.springframework.transaction.annotation.Transactional;
import space.util.RepositoryException;

import java.util.List;
import java.util.Optional;


public interface Repo<T>  {
     Optional<T> findById(int id) throws RepositoryException;

     List<T> getAll() throws RepositoryException;

     Optional<T> save(T item) throws RepositoryException;

     boolean delete(int id) throws RepositoryException;
}
