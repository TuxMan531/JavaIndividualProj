DROP TABLE IF EXISTS watchlist_entry;
DROP TABLE IF EXISTS movie;
DROP TABLE IF EXISTS user;

CREATE TABLE user (
  id INT NOT NULL AUTO_INCREMENT,
  username VARCHAR(50) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY user_username_uindex (username)
);

CREATE TABLE movie (
  id INT NOT NULL AUTO_INCREMENT,
  title VARCHAR(255) NOT NULL,
  release_year INT NOT NULL,
  imdb_id VARCHAR(20) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY movie_imdb_id_uindex (imdb_id)
);

CREATE TABLE watchlist_entry (
  id INT NOT NULL AUTO_INCREMENT,
  user_id INT NOT NULL,
  movie_id INT NOT NULL,
  watched BOOLEAN NOT NULL DEFAULT FALSE,
  PRIMARY KEY (id),
  UNIQUE KEY watchlist_entry_user_movie_uindex (user_id, movie_id),
  CONSTRAINT watchlist_entry_user_fk FOREIGN KEY (user_id) REFERENCES user (id),
  CONSTRAINT watchlist_entry_movie_fk FOREIGN KEY (movie_id) REFERENCES movie (id)
);

INSERT INTO user (id, username) VALUES
(1,'moviefan1'),
(2,'moviefan2');

INSERT INTO movie (id, title, release_year, imdb_id) VALUES
(1,'Dune',2021,'tt1160419'),
(2,'Dune',1984,'tt0087182'),
(3,'Interstellar',2014,'tt0816692'),
(4,'The Matrix',1999,'tt0133093');

INSERT INTO watchlist_entry (id, user_id, movie_id, watched) VALUES
(1,1,1,FALSE),
(2,1,4,TRUE),
(3,2,1,FALSE);
