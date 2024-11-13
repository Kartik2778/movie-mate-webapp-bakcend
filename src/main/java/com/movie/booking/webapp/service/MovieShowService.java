package com.movie.booking.webapp.service;

import com.movie.booking.webapp.model.MovieShow;
import com.movie.booking.webapp.model.ShowSeat;
import com.movie.booking.webapp.request.CreateMovieShowRequest;
import com.movie.booking.webapp.response.ShowSeatsDetails;

import java.util.List;


public interface MovieShowService {
    MovieShow createMovieShow(Long cinemaHallId, CreateMovieShowRequest movieShow);
    void deleteMovieShow(Long movieShowId);
    List<MovieShow> getMovieShowByCinemaHallId(Long cinemaHallId);
    List<MovieShow> getMovieShowByMovieNameAndCity(Long movieShowId, String city);
    List<ShowSeat>  getShowSeatByMovieShowId(Long movieShowId);
    ShowSeatsDetails getShowSeatDetailsByMovieShowId(Long movieShowId);
}
