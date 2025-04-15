package space.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import space.entity.Reservation;
import space.entity.SpaceType;
import space.util.RepositoryException;

import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class SpaceTypeRepo {
    @Autowired
    private EntityManagerFactory emf;

    public Optional<SpaceType> findById(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            SpaceType spaceType = em.find(SpaceType.class, id);
            return Optional.ofNullable(spaceType);
        }
    }

    public Optional<SpaceType> findByName(String name) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery(
                            "SELECT e FROM SpaceType e WHERE e.name= :name",
                            SpaceType.class)
                    .setParameter("name", name)
                    .getResultStream().findAny();
        }
    }

    public List<SpaceType> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            String queryString = "SELECT e FROM SpaceType e";
            Query q = em.createQuery(queryString);
            return q.getResultList();
        }
    }

    public SpaceType save(SpaceType item) throws RepositoryException {
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
                throw new RepositoryException("Failed to save a space type");
            }
        }
    }

    public void delete(int id) throws RepositoryException {
        try (EntityManager em = emf.createEntityManager()) {
            try {
                Query q = em.createQuery("DELETE FROM SpaceType e WHERE e.id = :id")
                        .setParameter("id", id);
                em.getTransaction().begin();
                q.executeUpdate();
                em.flush();
                em.clear();
                em.getTransaction().commit();
            } catch (Throwable ex) {
                em.getTransaction().rollback();
                log.error(ex.getMessage(), ex);
                throw new RepositoryException("Failed to delete a space type");
            }
        }
    }
}
