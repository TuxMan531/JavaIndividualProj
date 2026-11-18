package edu.matc.persistence;

import edu.matc.entity.Movie;
import edu.matc.entity.User;
import edu.matc.entity.WatchlistEntry;
import edu.matc.util.Database;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

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
    void findsEntryById() {
        User expectedUser = userDao.getById(1);
        Movie expectedMovie = movieDao.getById(4); // the matrix
        WatchlistEntry expectedEntry = new WatchlistEntry(expectedUser, expectedMovie, true);
        expectedEntry.setId(2);
        WatchlistEntry retrievedEntry = dao.getById(2);
        assertEquals(expectedEntry, retrievedEntry);
        assertEquals(expectedUser, retrievedEntry.getUser());
        assertEquals(expectedMovie, retrievedEntry.getMovie());
    }

    /** verifies all the sample entries are returned */
    @Test
    void listsAllEntries() {
        List<WatchlistEntry> entries = dao.getAll();
        assertEquals(3, entries.size()); // 3 in cleandb.sql
    }

    /** verifies one user's entries can be found by the user */
    @Test
    void findsEntriesForUser() {
        User user = userDao.getById(1);
        List<WatchlistEntry> entries = dao.getByPropertyEqual("user", user);
        assertEquals(2, entries.size()); // moviefan1 has dune and the matrix
    }

    /** verifies a new entry saves and can be read back */
    @Test
    void savesNewEntry() {
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

    /** verifies a user can't save the same movie twice, the unique key on the table stops it */
    @Test
    void rejectsSavingSameMovieTwice() {
        WatchlistEntry duplicateEntry = new WatchlistEntry(userDao.getById(1), movieDao.getById(1), false);
        assertThrows(ConstraintViolationException.class, () -> dao.insert(duplicateEntry));
    }

    /** verifies the rejected duplicate dosen't mess with the user's watchlist */
    @Test
    void keepsOneEntryAfterDuplicateSave() {
        User user = userDao.getById(1);
        WatchlistEntry duplicateEntry = new WatchlistEntry(user, movieDao.getById(1), false);
        assertThrows(ConstraintViolationException.class, () -> dao.insert(duplicateEntry));
        assertEquals(2, dao.getByPropertyEqual("user", user).size()); // still 2
    }

    /** verifies an entry can be marked as watched and it persits */
    @Test
    void marksEntryWatched() {
        WatchlistEntry entryToUpdate = dao.getById(1);
        entryToUpdate.setWatched(true);
        dao.update(entryToUpdate);
        WatchlistEntry entryAfterUpdate = dao.getById(1);
        assertEquals(entryToUpdate, entryAfterUpdate);
    }

    /** verifies an entry can be removed from a watchlist */
    @Test
    void removesEntry() {
        dao.delete(dao.getById(1));
        assertNull(dao.getById(1));
    }

    /** verifies a second user can save a movie someone else already saved */
    @Test
    void letsTwoUsersSaveSameMovie() {
        User user = userDao.getById(2);
        Movie movie = movieDao.getById(4); // moviefan1 already has this one
        WatchlistEntry newEntry = new WatchlistEntry(user, movie, false);
        int id = dao.insert(newEntry);
        WatchlistEntry insertedEntry = dao.getById(id);
        assertEquals(newEntry, insertedEntry);
        assertEquals(user, insertedEntry.getUser());
        assertEquals(movie, insertedEntry.getMovie());
    }

    /** verifies searching by user only gives back that user's entries */
    @Test
    void showsOnlyThatUsersEntries() {
        User user = userDao.getById(1);
        List<WatchlistEntry> entries = dao.getByPropertyEqual("user", user);
        for (WatchlistEntry entry : entries) {
            assertEquals(user, entry.getUser());
        }
    }

    /** verifies marking one user's entry watched leaves the other user's entry for the same movie unwatched */
    @Test
    void watchingKeepsOtherUsersEntryUnwatched() {
        WatchlistEntry entryToUpdate = dao.getById(1);
        entryToUpdate.setWatched(true);
        dao.update(entryToUpdate);
        assertFalse(dao.getById(3).isWatched()); // entry 3 is moviefan2's dune
    }

    /** verifies removing one user's entry leaves the other user's entry for the same movie alone */
    @Test
    void removingKeepsOtherUsersEntry() {
        dao.delete(dao.getById(1));
        assertNotNull(dao.getById(3));
    }

    /** verifies removing an entry dosen't delete the user or the movie too */
    @Test
    void removingKeepsUserAndMovie() {
        dao.delete(dao.getById(1));
        assertNotNull(userDao.getById(1));
        assertNotNull(movieDao.getById(1));
    }
}
