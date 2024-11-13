package com.movie.booking.webapp.controller;

import com.movie.booking.webapp.model.MovieShow;
import com.movie.booking.webapp.request.CreateMovieShowRequest;
import com.movie.booking.webapp.service.MovieShowService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin")
@AllArgsConstructor
public class MovieShowController {

    private final MovieShowService movieShowService;

    @GetMapping("cinema-hall/{id}/movie-shows")
    public ResponseEntity<List<MovieShow>> getAllMovieShowsByCinemaHallId(@PathVariable("id") Long cinemaHallId) {
        List<MovieShow> movieShows = movieShowService.getMovieShowByCinemaHallId(cinemaHallId);
        return ResponseEntity.ok(movieShows);
    }

    @PostMapping("{id}/create-movie-show")
    public ResponseEntity<MovieShow> createMovieShow(@PathVariable("id") Long cinemaHallId
            ,@RequestBody CreateMovieShowRequest movieShowRequest) {
        MovieShow movieShow = movieShowService.createMovieShow(cinemaHallId, movieShowRequest);
        return new ResponseEntity<>(movieShow, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}/delete-movie-show")
    public void getMovieShow(@PathVariable("id") Long cinemaHallId){
        movieShowService.deleteMovieShow(cinemaHallId);
    }
}
