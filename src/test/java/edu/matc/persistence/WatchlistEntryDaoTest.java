package edu.matc.persistence;

import edu.matc.entity.Movie;
import edu.matc.entity.User;
import edu.matc.entity.WatchlistEntry;
import edu.matc.util.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/** tests watchlist entries going through the generic dao */
class WatchlistEntryDaoTest {

    GenericDao<WatchlistEntry> dao;
    GenericDao<User> userDao;
    GenericDao<Movie> movieDao;

    /** puts the test database back to the sample data and makes the daos, FOR EACH test */
    @BeforeEach
    void setUp() {
        Database database = Database.getInstance();
        database.runSQL("cleandb.sql");

        dao = new GenericDao<>(WatchlistEntry.class);
        userDao = new GenericDao<>(User.class);
        movieDao = new GenericDao<>(Movie.class);
    }

    /** verifies a new entry saves and can be read back */
    @Test
    void insertSuccess() {
        User user = userDao.getById(2);
        Movie movie = movieDao.getById(3); // interstellar
        WatchlistEntry newEntry = new WatchlistEntry(user, movie, false);
        int id = dao.insert(newEntry);
        assertNotEquals(0, id);
        WatchlistEntry insertedEntry = dao.getById(id);
        assertEquals(newEntry, insertedEntry);
        assertEquals(user, insertedEntry.getUser());
        assertEquals(movie, insertedEntry.getMovie());
    }
}
