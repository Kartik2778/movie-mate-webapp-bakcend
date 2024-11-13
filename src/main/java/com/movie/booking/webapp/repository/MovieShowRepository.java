package com.movie.booking.webapp.repository;

import com.movie.booking.webapp.model.CinemaHall;
import com.movie.booking.webapp.model.MovieShow;
import com.movie.booking.webapp.model.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface MovieShowRepository extends JpaRepository<MovieShow, Long> {

    @Query("SELECT m FROM MovieShow m WHERE m.cinemaHall.hall_id = :cinemaHallId AND m.showDate = :showDate")
    List<MovieShow> findByCinemaHallIdAndShowDate(Long cinemaHallId, LocalDate showDate);

    @Query("SELECT m FROM MovieShow m WHERE m.movie.movie_id = :movieId AND m.cinemaHall.theater.address.city = :city")
    List<MovieShow> searchMovieShow(Long movieId,String city);

    List<MovieShow> findByCinemaHall(CinemaHall cinemaHall);
}
