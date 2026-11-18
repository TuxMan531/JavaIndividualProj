package edu.matc.persistence;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

/**
 * one dao for every entity so i dont have to write a new one for each table
 * based on the GenericDao from the week 5 course video
 * @param <T> the type of enitity this dao works with
 */
public class GenericDao<T> {

    private Class<T> type;
    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * creates a dao for one entity type
     * @param type the entity type, ex WatchlistEntry.class
     */
    public GenericDao(Class<T> type) {
        this.type = type;
    }

    /**
     * gets an entity by id
     * @param id the id to search by
     * @return the matching entity, or null if it doesn't exist
     */
    public T getById(int id) {
        try (Session session = getSession()) {
            return session.get(type, id);
        }
    }

    /** @return every entity of this type */
    public List<T> getAll() {
        try (Session session = getSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<T> query = builder.createQuery(type);
            query.from(type);
            List<T> entities = session.createSelectionQuery(query).getResultList();
            logger.debug("Retrieved {} {} records", entities.size(), type.getSimpleName());
            return entities;
        }
    }

    /**
     * inserts a new entity
     * @param entity the entity to insert
     * @return the id the database gens
     */
    public int insert(T entity) {
        try (Session session = getSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            int id = (int) session.getIdentifier(entity);
            logger.debug("Inserted {} with id {}", type.getSimpleName(), id);
            return id;
        }
    }

    /**
     * updates an entity
     * @param entity the entity with its updated values
     */
    public void update(T entity) {
        try (Session session = getSession()) {
            Transaction transaction = session.beginTransaction();
            T updatedEntity = session.merge(entity);
            transaction.commit();
            logger.debug("Updated {} with id {}", type.getSimpleName(), session.getIdentifier(updatedEntity));
        }
    }

    /** @return an open session from the session factory */
    private Session getSession() {
        return SessionFactoryProvider.getSessionFactory().openSession();
    }
}
