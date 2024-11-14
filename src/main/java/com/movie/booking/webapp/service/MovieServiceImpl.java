package com.movie.booking.webapp.service;

import com.movie.booking.webapp.model.Movie;
import com.movie.booking.webapp.repository.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MovieServiceImpl implements MovieService{

    private MovieRepository movieRepository;

    @Override
    public Movie getMovieById(Long movieId) {
        return movieRepository.findById(movieId).orElseThrow(
                () -> new RuntimeException("movie not found")
        );
    }

    @Override
    public Movie createMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public Movie updateMovie(Long movieId,Movie movie) {
        Movie existingMovie = movieRepository.findById(movieId).orElseThrow(
                () -> new RuntimeException("Movie not found")
        );
        existingMovie.setTitle(movie.getTitle());
        existingMovie.setDescription(movie.getDescription());
        existingMovie.setDuration(movie.getDuration());
        existingMovie.setLanguages(movie.getLanguages());
        existingMovie.setType(movie.getType());
        existingMovie.setReleaseDate(movie.getReleaseDate());
        return movieRepository.save(existingMovie);
    }

    @Override
    public void deleteMovie(Long movieId) {
        Movie existingMovie = movieRepository.findById(movieId).orElseThrow(
                () -> new RuntimeException("Movie not found")
        );
        movieRepository.delete(existingMovie);
    }

    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.getAllMoviesSortedByTime();
    }

    @Override
    public List<Movie> getLatestMovies() {
        return movieRepository.getLatestMovies();
    }

    @Override
    public List<Movie> searchMovie(String keyword) {
        List<Movie> movies = movieRepository.searchMovies(keyword);
        if(movies.isEmpty()){
            throw new RuntimeException("No movies found");
        }
        return movies;
    }
}
