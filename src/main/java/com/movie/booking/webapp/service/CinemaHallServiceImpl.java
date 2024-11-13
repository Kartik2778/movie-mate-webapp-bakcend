package com.movie.booking.webapp.service;

import com.movie.booking.webapp.model.*;
import com.movie.booking.webapp.repository.CinemaHallRepository;
import com.movie.booking.webapp.repository.SeatRepository;
import com.movie.booking.webapp.repository.TheaterRepository;
import com.movie.booking.webapp.request.CinemaHallRequest;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class CinemaHallServiceImpl implements CinemaHallService {

    private CinemaHallRepository cinemaHallRepository;
    private TheaterRepository theaterRepository;
    private SeatRepository seatRepository;

    @Override
    public CinemaHall createCinemaHall(CinemaHallRequest cinemaHallRequest) {
        Theater theater = theaterRepository.findByName(cinemaHallRequest.getTheaterName()).orElseThrow(
                () -> new RuntimeException("There is no theater with  " + cinemaHallRequest.getTheaterName())
        );
        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setName(cinemaHallRequest.getName());
        cinemaHall.setNoOfVipSeats(cinemaHallRequest.getNoOfVipSeats());
        cinemaHall.setNoOfRegularSeats(cinemaHallRequest.getNoOfRegularSeats());
        cinemaHall.setNoOfPremiumSeats(cinemaHallRequest.getNoOfPremiumSeats());
        cinemaHall.setTotalSeats(cinemaHallRequest.getNoOfVipSeats()+ cinemaHallRequest.getNoOfRegularSeats()+ cinemaHallRequest.getNoOfPremiumSeats());
        cinemaHall.setTheater(theater);
        CinemaHall savedCinemaHall = cinemaHallRepository.save(cinemaHall);

        int cnt = 1;
        for(int i = 0; i < cinemaHallRequest.getNoOfRegularSeats(); i++) {
            Seat seat = new Seat();
            seat.setSeatType(SeatType.REGULAR);
            seat.setSeatNumber(cnt++);
            seat.setCinemaHall(savedCinemaHall);
            seatRepository.save(seat);
        }
        for(int i = 0; i < cinemaHallRequest.getNoOfPremiumSeats(); i++) {
            Seat seat = new Seat();
            seat.setSeatType(SeatType.PREMIUM);
            seat.setSeatNumber(cnt++);
            seat.setCinemaHall(savedCinemaHall);
            seatRepository.save(seat);
        }
        for(int i = 0; i < cinemaHallRequest.getNoOfVipSeats(); i++) {
            Seat seat = new Seat();
            seat.setSeatType(SeatType.VIP);
            seat.setSeatNumber(cnt++);
            seat.setCinemaHall(savedCinemaHall);
            seatRepository.save(seat);
        }
        return savedCinemaHall;
    }

    @Override
    public void deleteCinemaHall(Long cinemaHallId) {
        CinemaHall exsitingCinemaHall = cinemaHallRepository.findById(cinemaHallId).orElseThrow(
                () -> new RuntimeException("There is no CinemaHall with id " + cinemaHallId)
        );
        cinemaHallRepository.delete(exsitingCinemaHall);
    }

    @Override
    public List<CinemaHall> getAllCinemaHall() {
        return cinemaHallRepository.findAll();
    }
}
