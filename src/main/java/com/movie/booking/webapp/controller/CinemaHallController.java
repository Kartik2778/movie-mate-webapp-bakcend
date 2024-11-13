package com.movie.booking.webapp.controller;

import com.movie.booking.webapp.model.CinemaHall;
import com.movie.booking.webapp.request.CinemaHallRequest;
import com.movie.booking.webapp.service.CinemaHallService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin")
@AllArgsConstructor
public class CinemaHallController {

    private final CinemaHallService cinemaHallService;

    @PostMapping("create-cinemaHall")
    public ResponseEntity<CinemaHall> createCinemaHall(@RequestBody CinemaHallRequest cinemaHallRequest) {
        return new ResponseEntity<>(cinemaHallService.createCinemaHall(cinemaHallRequest), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}/delete-cinemaHall")
    void deleteCinemaHall(@PathVariable("id") Long cinemaHallId) {
        cinemaHallService.deleteCinemaHall(cinemaHallId);
    }


    @GetMapping("all-cinema-hall")
    public ResponseEntity<List<CinemaHall>> getAllCinemaHalls(){
        return ResponseEntity.ok(cinemaHallService.getAllCinemaHall());
    }
}
