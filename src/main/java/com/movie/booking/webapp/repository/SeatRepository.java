package com.movie.booking.webapp.repository;

import com.movie.booking.webapp.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, Long> {
}
