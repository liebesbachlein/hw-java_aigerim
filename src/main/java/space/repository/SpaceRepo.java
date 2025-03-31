package space.repository;


import jakarta.persistence.*;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.transaction.annotation.Transactional;
import space.entity.Space;
import space.util.RepositoryException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SpaceRepo implements Repo<Space> {
    @PersistenceContext
    private EntityManager em;

    public Optional<Space> findById(int id) throws RepositoryException {
        try {
            Space space = em.find(Space.class, id);
            return Optional.of(space);
        } catch (Exception e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    public List<Space> getAll() throws RepositoryException {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Space> cr = cb.createQuery(Space.class);
            Root<Space> root = cr.from(Space.class);
            cr.select(root);
            Query query = em.createQuery(cr);
            return query.getResultList();
        } catch (Exception e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Transactional
    public Optional<Space> save(Space item) throws RepositoryException {
       //EntityTransaction transaction = em.getTransaction();
        try {
         //   transaction.begin();
            em.persist(item);
            //em.flush();
           // transaction.commit();
            return Optional.of(item);
        } catch (Exception e) {
           // transaction.rollback();
            throw new RepositoryException(e.getMessage());
        }
    }

    @Transactional
    public boolean delete(int id) throws RepositoryException {
        //EntityTransaction transaction = em.getTransaction();
        try {
            //transaction.begin();
            Query deleteParent = em.createQuery("delete from Space where id = :id")
                    .setParameter("id", id);
            Query deleteChildren = em.createQuery("delete from Reservation e where e.space.id = :id").setParameter("id", id);
            deleteChildren.executeUpdate();
            int num = deleteParent.executeUpdate();
            em.flush();
            //transaction.commit();
            return num > 0;
        } catch (Exception e) {
            //transaction.rollback();
            throw new RepositoryException(e.getMessage());
        }
    }
}
