package space.repository;

import jakarta.persistence.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import space.entity.Reservation;
import space.util.RepositoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Slf4j
@Repository
public class ReservationRepo {
    @Autowired
    private EntityManagerFactory emf;

    public Optional<Reservation> findById(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            Reservation reservation = em.find(Reservation.class, id);
            return Optional.ofNullable(reservation);
        }
    }

    public List<Reservation> findBySpaceId(int spaceId) {
        try (EntityManager em = emf.createEntityManager()) {
            List<Reservation>  reservations
                    = em.createQuery(
                            "SELECT e FROM Reservation e WHERE e.space.id= :spaceId",
                            Reservation.class)
                    .setParameter("spaceId", spaceId)
                    .getResultList();;
            return reservations;
        }
    }

    public List<Reservation> findByOwnerId(int ownerId) {
        try (EntityManager em = emf.createEntityManager()) {
            List<Reservation>  reservations
                    = em.createQuery(
                            "SELECT e FROM Reservation e WHERE e.owner.id= :ownerId",
                            Reservation.class)
                    .setParameter("ownerId", ownerId)
                    .getResultList();;
            return reservations;
        }
    }

    public List<Reservation> findBySpaceIdAndDateAndTime(
            int spaceId,
            LocalDate date,
            LocalTime hour) {
        try (EntityManager em = emf.createEntityManager()) {
            String queryString =
                """
                SELECT e FROM Reservation e
                WHERE e.date= :date
                AND e.space.id= :spaceId
                AND e.hour= :hour
                """;
            Query q = em.createQuery(queryString)
                    .setParameter("date", date)
                    .setParameter("spaceId", spaceId)
                    .setParameter("hour", hour);
            return q.getResultList();
        }
    }

    public List<Reservation> findBySpaceIdAndDate(
            int spaceId,
            LocalDate date) {
        try (EntityManager em = emf.createEntityManager()) {
            String queryString =
                    """
                    SELECT e FROM Reservation e
                    WHERE e.date= :date
                    AND e.space.id= :spaceId
                    """;
            Query q = em.createQuery(queryString)
                    .setParameter("date", date)
                    .setParameter("spaceId", spaceId);
            return q.getResultList();
        }
    }

    public List<Reservation> getAll() throws RepositoryException {
        try (EntityManager em = emf.createEntityManager()) {
            String queryString = "SELECT e FROM Reservation e";
            Query q = em.createQuery(queryString);
            return q.getResultList();
        }
    }

    @Transactional
    public Reservation save(Reservation item) throws RepositoryException {
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
                throw new RepositoryException("Failed to save a reservation");
            }
        }
    }

    @Transactional
    public void delete(int id) throws RepositoryException {
        try (EntityManager em = emf.createEntityManager()) {
            try {
                Query q = em.createQuery("DELETE FROM Reservation e WHERE e.id = :id")
                        .setParameter("id", id);
                em.getTransaction().begin();
                q.executeUpdate();
                em.flush();
                em.clear();
                em.getTransaction().commit();
            } catch (Throwable ex) {
                em.getTransaction().rollback();
                log.error(ex.getMessage(), ex);
                throw new RepositoryException("Failed to delete a reservation");
            }
        }
    }
}
