package com.movie.booking.webapp.controller;

import com.movie.booking.webapp.model.Booking;
import com.movie.booking.webapp.model.ShowSeat;
import com.movie.booking.webapp.model.User;
import com.movie.booking.webapp.request.BookingRequest;
import com.movie.booking.webapp.response.ShowSeatsDetails;
import com.movie.booking.webapp.service.BookingService;
import com.movie.booking.webapp.service.MovieShowService;
import com.movie.booking.webapp.service.UserService;
import com.razorpay.RazorpayException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user")
@AllArgsConstructor
public class UserController {

    private UserService userService;
    private MovieShowService movieShowService;
    private BookingService bookingService;


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

    @PostMapping("/create-booking")
    public ResponseEntity<Booking> createBooking(@RequestHeader("Authorization") String token, @RequestBody BookingRequest bookingRequest) throws RazorpayException {
        Booking booking = bookingService.createBooking(token,bookingRequest);
        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }

    @PostMapping("/confirm-booking")
    public void confirmBooking(@RequestBody Map<String, Object> payload) {
         bookingService.confirmBooking(payload.get("razorpay_order_id").toString());
    }

    @PostMapping("/payment-failed")
    public void paymentFailed(@RequestBody Map<String, Object> payload) {
        bookingService.releaseSeats(payload.get("razorpay_order_id").toString());
    }

    @GetMapping("all-bookings")
    public ResponseEntity<List<Booking>> getAllBookings(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(bookingService.getAllBookingsByUserId(token));
    }
}
