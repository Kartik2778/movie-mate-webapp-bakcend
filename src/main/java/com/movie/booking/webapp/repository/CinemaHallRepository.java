package com.movie.booking.webapp.repository;

import com.movie.booking.webapp.model.CinemaHall;
import com.movie.booking.webapp.model.Theater;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CinemaHallRepository extends JpaRepository<CinemaHall, Long> {
    Optional<List<CinemaHall>> findCinemaHallsByTheater(Theater theater);
}
