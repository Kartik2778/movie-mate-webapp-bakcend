package com.movie.booking.webapp.controller;

import com.movie.booking.webapp.model.CinemaHall;
import com.movie.booking.webapp.model.Theater;
import com.movie.booking.webapp.repository.TheaterRepository;
import com.movie.booking.webapp.service.TheaterService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin")
@AllArgsConstructor
public class TheaterController {

    private TheaterService theaterService;

    @PostMapping("create-theater")
    public ResponseEntity<Theater> createTheater(@RequestBody Theater theater) {
        Theater createdTheater = theaterService.createTheater(theater);
        return new ResponseEntity<>(createdTheater, HttpStatus.CREATED);
    }

    @GetMapping("all-theater")
    public ResponseEntity<List<Theater>> createTheater(){
       return ResponseEntity.ok(theaterService.getAllTheaters());
    }

    @PutMapping("{id}/update-theater")
    public ResponseEntity<Theater> getTheater(@PathVariable Long id,@RequestBody Theater theater) {
        return ResponseEntity.ok(theaterService.updateTheater(id,theater));
    }

    @DeleteMapping("{id}/delete-theater")
    public void deleteTheater(@PathVariable Long id) {
        theaterService.deleteTheater(id);
    }
}
