package com.movie.booking.webapp.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CinemaHallRequest {

    private String theaterName;

    private String name;

    private int noOfRegularSeats;

    private int noOfPremiumSeats;

    private int noOfVipSeats;
}
