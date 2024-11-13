package com.movie.booking.webapp.repository;

import com.movie.booking.webapp.model.Booking;
import com.movie.booking.webapp.model.MovieShow;
import com.movie.booking.webapp.model.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {
    List<ShowSeat> findAllByMovieShow(MovieShow movieShow);
}
