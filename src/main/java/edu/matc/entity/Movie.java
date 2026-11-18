package edu.matc.entity;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import java.util.Objects;

/** one release of a movie, the year is what tells apart movies w/ the same title such as very creativly nammed dune and dune */
@Entity(name = "Movie")
@Table(name = "movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int id;

    private String title;

    @Column(name = "release_year")
    private int releaseYear;

    @Column(name = "imdb_id")
    private String imdbId; // looks like tt1160419

    /** creates an empty movie for hibernate */
    public Movie() {
    }

    /**
     * creates a movie with its title, year and imdb id
     * @param title the title
     * @param releaseYear the year the movie came out
     * @param imdbId the imdb id
     */
    public Movie(String title, int releaseYear, String imdbId) {
        this.title = title;
        this.releaseYear = releaseYear;
        this.imdbId = imdbId;
    }

    /** @return the movie id */
    public int getId() {
        return id;
    }

    /** @param id the movie id */
    public void setId(int id) {
        this.id = id;
    }

    /** @return the title */
    public String getTitle() {
        return title;
    }

    /** @param title the title */
    public void setTitle(String title) {
        this.title = title;
    }

    /** @return the year the movie came out */
    public int getReleaseYear() {
        return releaseYear;
    }

    /** @param releaseYear the year the movie came out */
    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    /** @return the imdb id */
    public String getImdbId() {
        return imdbId;
    }

    /** @param imdbId the imdb id */
    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Movie movie = (Movie) object;
        return id == movie.id && releaseYear == movie.releaseYear
                && Objects.equals(title, movie.title) && Objects.equals(imdbId, movie.imdbId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, releaseYear, imdbId);
    }
}
