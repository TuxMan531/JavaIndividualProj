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

    /** verifies a sample entry comes back with its user and movie */
    @Test
    void getByIdSuccess() {
        User expectedUser = userDao.getById(1);
        Movie expectedMovie = movieDao.getById(4); // the matrix
        WatchlistEntry expectedEntry = new WatchlistEntry(expectedUser, expectedMovie, true);
        expectedEntry.setId(2);
        WatchlistEntry retrievedEntry = dao.getById(2);
        assertEquals(expectedEntry, retrievedEntry);
        assertEquals(expectedUser, retrievedEntry.getUser());
        assertEquals(expectedMovie, retrievedEntry.getMovie());
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
