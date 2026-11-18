package edu.matc.entity;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import java.util.Objects;

/** one movie saved on one user's watchlist, and wether they've watched it yet */
@Entity(name = "WatchlistEntry")
@Table(name = "watchlist_entry")
public class WatchlistEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int id;

    @ManyToOne // a user can have lots of entries, maybe unlimited? idk if it should or shouldn't be but ill leave it like this
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne // and a movie can be on lots of watchlists
    @JoinColumn(name = "movie_id")
    private Movie movie;

    private boolean watched;

    /** creates an empty entry for hibernate */
    public WatchlistEntry() {
    }

    /**
     * creates an entry for a user and a movie
     * @param user the user who saved the movie
     * @param movie the saved movie
     * @param watched true if they already watched it
     */
    public WatchlistEntry(User user, Movie movie, boolean watched) {
        this.user = user;
        this.movie = movie;
        this.watched = watched;
    }

    /** @return the entry id */
    public int getId() {
        return id;
    }

    /** @param id the entry id */
    public void setId(int id) {
        this.id = id;
    }

    /** @return the user who saved the movie */
    public User getUser() {
        return user;
    }

    /** @param user the user who saved the movie */
    public void setUser(User user) {
        this.user = user;
    }

    /** @return the saved movie */
    public Movie getMovie() {
        return movie;
    }

    /** @param movie the saved movie */
    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    /** @return true if the user has watched the movie */
    public boolean isWatched() {
        return watched;
    }

    /** @param watched true if the user has watched the movie */
    public void setWatched(boolean watched) {
        this.watched = watched;
    }

    // only id and watched in here | the tests check the user and movie on their own
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        WatchlistEntry entry = (WatchlistEntry) object;
        return id == entry.id && watched == entry.watched;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, watched);
    }
}
