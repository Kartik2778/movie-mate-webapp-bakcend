package com.movie.booking.webapp.service;

import com.movie.booking.webapp.model.CinemaHall;
import com.movie.booking.webapp.request.CinemaHallRequest;

import java.util.List;

public interface CinemaHallService {
    CinemaHall createCinemaHall(CinemaHallRequest cinemaHallRequest);
    void deleteCinemaHall(Long cinemaHallId);
    List<CinemaHall> getAllCinemaHall();
}
