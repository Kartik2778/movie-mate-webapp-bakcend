package com.movie.booking.webapp.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int seatNumber;
    private SeatType seatType;
    private Long price;
    @JsonIgnore
    @ManyToOne
    private MovieShow movieShow;

    private Boolean status = Boolean.FALSE;
}
