package com.movie.booking.webapp.repository;

import com.movie.booking.webapp.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    Optional<Movie> findByTitle(String title);

    @Query("SELECT m FROM Movie m WHERE LOWER(m.title) LIKE LOWER(CONCAT('%',:keyword,'%') ) " +
            "OR LOWER(m.releaseDate) LIKE LOWER(CONCAT('%',:keyword,'%') )" +
            "OR LOWER(m.type) LIKE LOWER(CONCAT('%',:keyword,'%') )")
    Optional<List<Movie>> searchMovies(String keyword);

    @Query("SELECT m FROM Movie m ORDER BY (m.releaseDate) DESC")
    List<Movie> getAllMoviesSortedByTime();

    @Query("SELECT m FROM Movie m ORDER BY (m.releaseDate) DESC LIMIT 8")
    List<Movie> getLatestMovies();
}
