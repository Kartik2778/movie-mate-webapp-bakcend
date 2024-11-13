package com.movie.booking.webapp.controller;

import com.movie.booking.webapp.model.Movie;
import com.movie.booking.webapp.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin")
@AllArgsConstructor
public class MovieController {

    private final MovieService movieService;


    @PostMapping("create-movie")
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        Movie createdMovie = movieService.createMovie(movie);
        return new ResponseEntity<>(createdMovie, HttpStatus.CREATED);
    }

    @PutMapping("{id}/update-movie")
    public ResponseEntity<Movie> updateMovie(@PathVariable("id") Long movieId, @RequestBody Movie movie) {
        return ResponseEntity.ok(movieService.updateMovie(movieId, movie));
    }

    @DeleteMapping("{id}/delete-movie")
    public void deleteMovie(@PathVariable("id") Long movieId) {
        movieService.deleteMovie(movieId);
    }

    @GetMapping("all-movies")
    public ResponseEntity<List<Movie>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

}
