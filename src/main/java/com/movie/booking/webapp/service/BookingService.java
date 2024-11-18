package com.movie.booking.webapp.service;

import com.movie.booking.webapp.model.Booking;
import com.movie.booking.webapp.request.BookingRequest;
import com.razorpay.RazorpayException;

import java.util.List;

public interface BookingService {
    Booking createBooking(String token, BookingRequest bookingRequest) throws RazorpayException;
    void confirmBooking(String razorpay_order_id);
    void releaseSeats(String razorpay_order_id);
    List<Booking> getAllBookingsByUserId(String token);
}
