package com.movie.booking.webapp.service;

import com.movie.booking.webapp.model.CinemaHall;
import com.movie.booking.webapp.model.Theater;

import java.util.List;


public interface TheaterService {
    Theater createTheater(Theater theater);
    List<Theater> getAllTheaters();
    Theater updateTheater(Long id,Theater theater);
    void deleteTheater(Long id);
    List<String> getAllCities();
}
