package com.movie.booking.webapp.controller;

import com.movie.booking.webapp.model.Movie;
import com.movie.booking.webapp.model.MovieShow;
import com.movie.booking.webapp.model.ShowSeat;
import com.movie.booking.webapp.service.MovieService;
import com.movie.booking.webapp.service.MovieShowService;
import com.movie.booking.webapp.service.TheaterService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("public")
@AllArgsConstructor
public class PublicController {

    private final MovieService movieService;
    private final MovieShowService movieShowService;
    private TheaterService theaterService;


    @GetMapping("{id}/movie")
    public ResponseEntity<Movie> getMovieById(@PathVariable("id") Long movieId) {
        return ResponseEntity.ok(movieService.getMovieById(movieId));
    }

    @GetMapping("movies/search")
    public ResponseEntity<List<Movie>> searchMovie(@RequestParam("query") String keyword) {
        return ResponseEntity.ok(movieService.searchMovie(keyword));
    }

    @GetMapping("latest-movies")
    public ResponseEntity<List<Movie>> getLatestMovies() {
        return ResponseEntity.ok(movieService.getLatestMovies());
    }


    @GetMapping("{id}/movie-show")
    public ResponseEntity<List<MovieShow>> getMovieShows(@PathVariable("id") Long movieId, @RequestParam("city") String city) {
        return ResponseEntity.ok(movieShowService.getMovieShowByMovieNameAndCity(movieId,city));
    }


    @GetMapping("all-cities")
    public ResponseEntity<List<String>> getAllCities() {
        return ResponseEntity.ok(theaterService.getAllCities());
    }
}
