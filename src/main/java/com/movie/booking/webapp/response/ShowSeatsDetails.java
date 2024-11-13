package com.movie.booking.webapp.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowSeatsDetails {
    private int noOfRegularSeats;
    private int noOfPremiumSeats;
    private int noOfVipSeats;
    private Long regularSeatPrice;
    private Long premiumSeatPrice;
    private Long vipSeatPrice;
}
