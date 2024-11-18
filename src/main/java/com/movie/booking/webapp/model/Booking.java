package com.movie.booking.webapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long booking_id;

    private String razorpayOrderId;

    @CreationTimestamp
    private LocalDateTime bookingDate;

    private int numberOfSeats;

    private Long totalPrice;

    private String status;

    @ManyToOne
    private MovieShow movieShow;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "show_seat_id")
    private List<ShowSeat> seats = new ArrayList<>();

    @ManyToOne
    private User user;
}
