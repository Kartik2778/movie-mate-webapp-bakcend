package com.movie.booking.webapp.service;


import com.movie.booking.webapp.model.Booking;
import com.movie.booking.webapp.model.User;

import java.util.List;

public interface UserService {
    User getUserByToken(String token);
    List<Booking> getAllBookings(String token);
    List<User> getAllUsers();
}
