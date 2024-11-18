package com.movie.booking.webapp.service;

import com.movie.booking.webapp.model.Booking;
import com.movie.booking.webapp.model.MovieShow;
import com.movie.booking.webapp.model.ShowSeat;
import com.movie.booking.webapp.model.User;
import com.movie.booking.webapp.repository.BookingRepository;
import com.movie.booking.webapp.repository.MovieShowRepository;
import com.movie.booking.webapp.repository.ShowSeatRepository;
import com.movie.booking.webapp.request.BookingRequest;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService{

    private final MovieShowRepository movieShowRepository;
    private final BookingRepository bookingRepository;
    private UserService userService;
    private ShowSeatRepository showSeatRepository;

    @Override
    public Booking createBooking(String token, BookingRequest bookingRequest) throws RazorpayException {
        String seats = null;
        for(Long seatId : bookingRequest.getSeatIds()) {
            ShowSeat showSeat = showSeatRepository.findById(seatId).get();
            if(showSeat.getStatus()){
                seats = showSeat.getSeatNumber()+" ";
            }
        }
        if(seats != null) {
            throw new RuntimeException("Seat no " + seats + " is already in Booked");
        }
        List<ShowSeat> showSeatList = new ArrayList<>();
        Long totalPrice = 0L;
        for (Long seatId : bookingRequest.getSeatIds()) {
            ShowSeat showSeat = showSeatRepository.findById(seatId).get();
            totalPrice += (showSeat.getPrice());
            showSeat.setStatus(true);
            showSeatRepository.save(showSeat);
            showSeatList.add(showSeat);
        }
        User user = userService.getUserByToken(token);
        MovieShow movieShow = movieShowRepository.findById(bookingRequest.getMovieShowId()).get();

        JSONObject bookingReq = new JSONObject();
        bookingReq.put("amount",totalPrice*100);
        bookingReq.put("currency","INR");
        bookingReq.put("receipt",user.getEmail());
        RazorpayClient razorpayClient = new RazorpayClient("rzp_test_b8iv7N5HFqzQ8O","ZhAi7GhP7LJbyPBBouWL7dte");
        Order order = razorpayClient.orders.create(bookingReq);

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setNumberOfSeats(bookingRequest.getSeatIds().size());
        booking.setTotalPrice(totalPrice);
        booking.setStatus(order.get("status"));
        booking.setMovieShow(movieShow);
        booking.setSeats(showSeatList);
        booking.setRazorpayOrderId(order.get("id"));

        return bookingRepository.save(booking);
    }

    @Override
    public void confirmBooking(String razorpayOrderId) {
        Booking booking = bookingRepository.findByRazorpayOrderId(razorpayOrderId);
        booking.setStatus("Confirmed");
        bookingRepository.save(booking);
    }

    @Override
    public void releaseSeats(String razorpayOrderId) {
        Booking booking = bookingRepository.findByRazorpayOrderId(razorpayOrderId);
        booking.setStatus("Payment Failed");
        List<ShowSeat> seats = booking.getSeats();
        for(ShowSeat showSeat : seats) {
            ShowSeat showSeat1 = showSeatRepository.findById(showSeat.getId()).get();
            showSeat1.setStatus(false);
            showSeatRepository.save(showSeat1);
        }
        bookingRepository.save(booking);
    }

    @Override
    public List<Booking> getAllBookingsByUserId(String token) {
       User user = userService.getUserByToken(token);
        return bookingRepository.findByUser(user);
    }
}
