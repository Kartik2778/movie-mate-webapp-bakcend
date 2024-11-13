package com.movie.booking.webapp.repository;

import com.movie.booking.webapp.model.Booking;
import com.movie.booking.webapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository  extends JpaRepository<Booking, Long> {
    List<Booking> findByUser(User user);
}
