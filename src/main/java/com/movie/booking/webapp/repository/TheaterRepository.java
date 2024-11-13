package com.movie.booking.webapp.repository;

import com.movie.booking.webapp.model.CinemaHall;
import com.movie.booking.webapp.model.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TheaterRepository extends JpaRepository<Theater, Long> {
    @Query("select distinct a.city from Address a,Theater t where a.id = t.address.id")
    List<String> getAllCities();

    Optional<Theater> findByName(String name);
}
