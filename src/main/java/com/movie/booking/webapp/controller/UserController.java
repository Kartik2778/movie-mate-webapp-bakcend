package com.movie.booking.webapp.controller;

import com.movie.booking.webapp.model.Booking;
import com.movie.booking.webapp.model.ShowSeat;
import com.movie.booking.webapp.model.User;
import com.movie.booking.webapp.response.ShowSeatsDetails;
import com.movie.booking.webapp.service.MovieShowService;
import com.movie.booking.webapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private MovieShowService movieShowService;

    @GetMapping("profile")
    public ResponseEntity<User> userProfile(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(userService.getUserByToken(token));
    }

    @GetMapping("bookings")
    public ResponseEntity<List<Booking>> userBookings(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(userService.getAllBookings(token));
    }

    @GetMapping("{id}/show-seats")
    public ResponseEntity<List<ShowSeat>> getShowSeatsById(@PathVariable("id") Long movieShowId) {
        List<ShowSeat> showSeats = movieShowService.getShowSeatByMovieShowId(movieShowId);
        return ResponseEntity.ok(showSeats);
    }

    @GetMapping("{id}/show-seat-details")
    public ResponseEntity<ShowSeatsDetails> getShowSeatsDetailsById(@PathVariable("id") Long movieShowId) {
        return ResponseEntity.ok(movieShowService.getShowSeatDetailsByMovieShowId(movieShowId));
    }
}
