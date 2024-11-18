package com.movie.booking.webapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MovieShow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long show_id;

    private LocalDate showDate;
    private LocalTime startTime;
    private LocalTime endTime;

    private String language;

    private String screenType;

    private Long regularSeatPrice;

    private Long premiumSeatPrice;

    private Long vipSeatPrice;

    @ManyToOne
    private CinemaHall cinemaHall;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "movieShow")
    private List<ShowSeat> showSeats = new ArrayList<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.PERSIST,mappedBy = "movieShow")
    private List<Booking> bookings = new ArrayList<>();

    @ManyToOne
    private Movie movie;
}
