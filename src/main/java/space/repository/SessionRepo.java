package space.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import space.entity.Session;
import space.util.RepositoryException;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class SessionRepo {
    @Autowired
    private EntityManagerFactory emf;

    public Optional<Session> findById(String id) {
        try (EntityManager em = emf.createEntityManager()) {
            Session session = em.find(Session.class, id);
            return Optional.ofNullable(session);
        }
    }

    public List<Session> findByEmail(String email) {
        try (EntityManager em = emf.createEntityManager()) {
            String queryString = "SELECT e FROM Session e WHERE e.user.email=email";
            Query q = em.createQuery(queryString).setParameter("email", email);
            return q.getResultList();
        }
    }

    public Session save(Session item) throws RepositoryException {
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
                throw new RepositoryException("Failed to save a session");
            }
        }
    }

    public void delete(String id) throws RepositoryException {
        try (EntityManager em = emf.createEntityManager()) {
            try {
                Query q = em.createQuery("DELETE FROM Session e WHERE e.id = :id")
                        .setParameter("id", id);
                em.getTransaction().begin();
                q.executeUpdate();
                em.flush();
                em.clear();
                em.getTransaction().commit();
            } catch (Throwable ex) {
                em.getTransaction().rollback();
                log.error(ex.getMessage(), ex);
                throw new RepositoryException("Failed to delete a session");
            }
        }
    }

    public void deleteInBatchByEmail(String email) throws RepositoryException {
        try (EntityManager em = emf.createEntityManager()) {
            try {
                Query q = em.createQuery("DELETE FROM Session e WHERE e.email = :email")
                        .setParameter("id", email);
                em.getTransaction().begin();
                q.executeUpdate();
                em.flush();
                em.clear();
                em.getTransaction().commit();
            } catch (Throwable ex) {
                em.getTransaction().rollback();
                log.error(ex.getMessage(), ex);
                throw new RepositoryException("Failed to delete sessions by email");
            }
        }
    }
}
