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
import jakarta.transaction.Transactional;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class SpaceRepo implements Repo<Space> {
    private final EntityManager em;

    public SpaceRepo() {
        em = DBConfig.getEntityManager();
    }

    public Optional<Space> findById(int id) {
        return Optional.of(em.find(Space.class, id));
    }

    public List<Space> getAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Space> cr = cb.createQuery(Space.class);
        Root<Space> root = cr.from(Space.class);
        cr.select(root);
        Query query = em.createQuery(cr);
        return query.getResultList();
    }


    public Optional<Space> save(Space item) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(item);
        transaction.commit();
        return Optional.of(item);
    }

    public boolean delete(int id) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Query deleteParent = em.createQuery("delete from Space where id = :id")
                .setParameter("id", id);
        Query deleteChildren = em.createQuery("delete from Reservation e where e.space.id = :id").setParameter("id", id);
        deleteChildren.executeUpdate();
        int num = deleteParent.executeUpdate();
        transaction.commit();
        return num > 0;
    }
}
