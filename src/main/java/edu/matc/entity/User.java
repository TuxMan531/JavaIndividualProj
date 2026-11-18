package edu.matc.entity;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import java.util.Objects;

/** a person who keeps a watchlist in movie hunter */
@Entity(name = "User")
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int id;

    private String username;

    /** creates an empty user for hibernate */
    public User() {
    }

    /**
     * creates a user with a username
     * @param username the username
     */
    public User(String username) {
        this.username = username;
    }

    /** @return the user id */
    public int getId() {
        return id;
    }

    /** @param id the user id */
    public void setId(int id) {
        this.id = id;
    }

    /** @return the username */
    public String getUsername() {
        return username;
    }

    /** @param username the username */
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        User user = (User) object;
        return id == user.id && Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }
}
