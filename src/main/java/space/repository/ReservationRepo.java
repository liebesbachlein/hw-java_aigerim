package space.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.transaction.annotation.Transactional;
import space.entity.Reservation;
import space.util.RepositoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.sql.*;
import java.util.*;
import java.sql.Date;

@Repository
public class ReservationRepo implements Repo<Reservation> {
    @Autowired
    private EntityManager em;

    public Optional<Reservation> findById(int id) throws RepositoryException {
        try {
            Reservation reservation = em.find(Reservation.class, id);
            return Optional.of(reservation);
        } catch (Exception e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    public List<Reservation> findBySpaceId(int spaceId) throws RepositoryException {
        try {
            List<Reservation>  reservations = em.createQuery("SELECT e FROM Reservation e WHERE e.space.id= :spaceId",
                            Reservation.class)
                    .setParameter("spaceId", spaceId)
                    .getResultList();;
            return reservations;
        } catch (Exception e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    public List<Reservation> findBySpaceIdAndDateAndTime(int spaceId, Date date, Time startHour, Time endHour) throws RepositoryException {
        try {
            String queryString = """
                SELECT e FROM Reservation e
                WHERE e.date= :date
                AND e.space.id= :spaceId 
                AND (( e.startHour>= :startHour AND e.startHour< :endHour )
                OR ( e.endHour> :startHour AND e.endHour<= :endHour )
                OR ( e.startHour<= :startHour AND e.endHour> :startHour ))
                """;
            Query q = em.createQuery(queryString)
                    .setParameter("date", date)
                    .setParameter("spaceId", spaceId)
                    .setParameter("startHour", startHour)
                    .setParameter("endHour", endHour);
            List<Reservation> reservations = q.getResultList();
            return reservations;
        } catch (Exception e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    public List<Reservation> getAll() throws RepositoryException {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Reservation> cr = cb.createQuery(Reservation.class);
            Root<Reservation> root = cr.from(Reservation.class);
            cr.select(root);
            Query query = em.createQuery(cr);
            return query.getResultList();
        } catch (Exception e) {
            throw new RepositoryException(e.getMessage());
        }
    }
    @Transactional
    public Optional<Reservation> save(Reservation item) throws RepositoryException {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(item);
            em.flush();
            em.clear();
            transaction.commit();
            return Optional.of(item);
        } catch (Exception e) {
            transaction.rollback();
            throw new RepositoryException(e.getMessage());
        }
    }

    @Transactional
    public boolean delete(int id) throws RepositoryException {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Query q = em.createQuery("delete from Reservation where id = :id")
                    .setParameter("id", id);
            int num = q.executeUpdate();
            em.flush();
            em.clear();
            transaction.commit();
            return num > 0;
        } catch (Exception e) {
            transaction.rollback();
            throw new RepositoryException(e.getMessage());
        }
    }
}
