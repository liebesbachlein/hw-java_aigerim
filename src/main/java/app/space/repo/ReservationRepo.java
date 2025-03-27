package app.space.repo;

import app.space.config.DBConfig;
import app.space.entity.Reservation;
import app.space.entity.Space;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;


import java.sql.*;
import java.util.*;
import java.sql.Date;

public class ReservationRepo implements Repo<Reservation> {
    private final EntityManager em;

    public ReservationRepo() {
        em = DBConfig.getEntityManager();
    }

    public Optional<Reservation> findById(int id) {
        return Optional.of(em.find(Reservation.class, id));
    }

    public List<Reservation> findBySpaceId(int spaceId) {
        return em.createQuery("SELECT e FROM Reservation e WHERE e.space.id= :spaceId",
                Reservation.class)
                .setParameter("spaceId", spaceId)
                .getResultList();
    }

    public List<Reservation> findBySpaceIdAndDateAndTime(int spaceId, Date date, Time startHour, Time endHour) {
        String queryString = "SELECT e FROM Reservation e"
                + " WHERE e.date= :date"
                + " AND e.space.id= :spaceId"
                + " AND ("
                + "( e.startHour>= :startHour AND e.startHour< endHour )"
                + " OR ( e.endHour> :startHour AND e.endHour<= :endHour )"
                + " OR ( e.startHour<= :startHour AND e.endHour> :startHour )"
                + ")";
        Query q = em.createQuery(queryString)
                .setParameter("date", date)
                .setParameter("spaceId", spaceId)
                .setParameter("startHour", startHour)
                .setParameter("endHour", endHour);
        return q.getResultList();
    }

    public List<Reservation> getAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Reservation> cr = cb.createQuery(Reservation.class);
        Root<Reservation> root = cr.from(Reservation.class);
        cr.select(root);
        Query query = em.createQuery(cr);
        return query.getResultList();
    }

    public Optional<Reservation> save(Reservation item) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(item);
        transaction.commit();
        return Optional.of(item);
    }

    public boolean delete(int id) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Query q = em.createQuery("delete from Reservation where id = :id")
                .setParameter("id", id);
        int num = q.executeUpdate();
        transaction.commit();
        return num > 0;
    }
}
