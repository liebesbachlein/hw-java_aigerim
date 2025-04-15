package space.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import space.entity.Reservation;
import space.entity.User;
import space.util.RepositoryException;

import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class UserRepo {
    @Autowired
    private EntityManagerFactory emf;

    public Optional<User> findById(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            User user = em.find(User.class, id);
            return Optional.ofNullable(user);
        }
    }

    public Optional<User> findByEmail(String email) {
        try (EntityManager em = emf.createEntityManager()) {
             return em.createQuery(
                            "SELECT e FROM User e WHERE e.email= :email",
                            User.class)
                    .setParameter("email", email)
                    .getResultStream().findAny();
        }
    }

    public List<User> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            String queryString = "SELECT e FROM User";
            Query q = em.createQuery(queryString);
            return q.getResultList();
        }
    }

    public User save(User item) throws RepositoryException {
        try (EntityManager em = emf.createEntityManager()) {
            try {
                em.getTransaction().begin();
                em.persist(item);
                em.flush();
                em.clear();
                em.getTransaction().commit();
                return item;
            } catch (Throwable ex) {
                em.getTransaction().rollback();
                log.error(ex.getMessage(), ex);
                throw new RepositoryException("Failed to save a user");
            }
        }
    }

    public void delete(int id) throws RepositoryException {
        try (EntityManager em = emf.createEntityManager()) {
            try {
                Query q = em.createQuery("DELETE FROM User e WHERE e.id = :id")
                        .setParameter("id", id);
                em.getTransaction().begin();
                q.executeUpdate();
                em.flush();
                em.clear();
                em.getTransaction().commit();
            } catch (Throwable ex) {
                em.getTransaction().rollback();
                log.error(ex.getMessage(), ex);
                throw new RepositoryException("Failed to delete a user");
            }
        }
    }
}
