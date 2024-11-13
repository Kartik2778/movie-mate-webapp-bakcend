package com.movie.booking.webapp.service;

import com.movie.booking.webapp.model.Movie;

import java.util.List;

public interface MovieService {
    Movie getMovieById(Long movieId);
    Movie createMovie(Movie movie);
    Movie updateMovie(Long movieId,Movie movie);
    void deleteMovie(Long movieId);
    List<Movie> getAllMovies();
    List<Movie> getLatestMovies();
    List<Movie> searchMovie(String keyword);
}
