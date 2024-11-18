package com.movie.booking.webapp.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequest {
    private Long movieShowId;
    private List<Long> seatIds = new ArrayList<>();
}
