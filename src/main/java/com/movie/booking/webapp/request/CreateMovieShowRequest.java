package com.movie.booking.webapp.request;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class CreateMovieShowRequest {

    private LocalDate showDate;

    private LocalTime startTime;

    private String language;

    private String screenType;

    private Long regularSeatPrice;

    private Long premiumSeatPrice;

    private Long vipSeatPrice;

    private String movieName;
}
