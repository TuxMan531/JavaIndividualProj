package edu.matc.persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

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

    /** @return an open session from the session factory */
    private Session getSession() {
        return SessionFactoryProvider.getSessionFactory().openSession();
    }
}
