package com.movie.booking.webapp.service;

import com.movie.booking.webapp.model.*;
import com.movie.booking.webapp.repository.*;
import com.movie.booking.webapp.request.CreateMovieShowRequest;
import com.movie.booking.webapp.response.ShowSeatsDetails;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
@AllArgsConstructor
public class MovieShowServiceImpl implements MovieShowService{

    private MovieShowRepository movieShowRepository;
    private CinemaHallRepository cinemaHallRepository;
    private MovieRepository movieRepository;
    private ShowSeatRepository showSeatRepository;

    @Override
    public MovieShow createMovieShow(Long cinemaHallId ,CreateMovieShowRequest movieShowRequest) {
        String message = "Their is no movie with name "+movieShowRequest.getMovieName()+" is present in the database";
        Movie movie = movieRepository.findByTitle(movieShowRequest.getMovieName()).orElseThrow(
            () -> new RuntimeException(message)
        );
        CinemaHall cinemaHall = cinemaHallRepository.findById(cinemaHallId).orElseThrow(
                () -> new RuntimeException("cinemaHall with id "+cinemaHallId+" not found")
        );
        List<MovieShow> existingMovieShows = movieShowRepository.findByCinemaHallIdAndShowDate(cinemaHallId,movieShowRequest.getShowDate());

        boolean flag = false;
        int startTimeOfMovieShow = movieShowRequest.getStartTime().getHour();
        startTimeOfMovieShow += movieShowRequest.getStartTime().getMinute();
        LocalTime endt = movieShowRequest.getStartTime().plusHours(movie.getDuration().getHour()).plusMinutes(movie.getDuration().getMinute());
        int endTimeOfMovieShow = endt.getHour();
        endTimeOfMovieShow += endt.getMinute();
        for(MovieShow movieShow : existingMovieShows){
            int startTime = movieShow.getStartTime().getHour();
            startTime += movieShow.getStartTime().getMinute();
            int endTime = movieShow.getEndTime().getHour();
            endTime += movieShow.getEndTime().getMinute();
            if((startTime >= startTimeOfMovieShow && startTimeOfMovieShow <= endTime) || (startTime >= endTimeOfMovieShow && endTimeOfMovieShow <= endTime)){
                flag = true;
                break;
            }
        }
        if(flag) {
            throw new RuntimeException("Their is already one show running the cinema hall at the same");
        }

        MovieShow movieShow = getMovieShow(movieShowRequest,cinemaHall,movie);
        MovieShow savedMovieShow = movieShowRepository.save(movieShow);
        for(Seat seat : cinemaHall.getSeats()){
            ShowSeat showSeat = new ShowSeat();
            showSeat.setMovieShow(savedMovieShow);
            showSeat.setSeatNumber(seat.getSeatNumber());
            showSeat.setSeatType(seat.getSeatType());
            if(showSeat.getSeatType().equals(SeatType.REGULAR)){
                showSeat.setPrice(movieShowRequest.getRegularSeatPrice());
            }
            else if(showSeat.getSeatType().equals(SeatType.PREMIUM)){
                showSeat.setPrice(movieShowRequest.getPremiumSeatPrice());
            }
            else {
                showSeat.setPrice(movieShowRequest.getVipSeatPrice());
            }
            showSeatRepository.save(showSeat);
        }
        return savedMovieShow;
    }

    @Override
    public void deleteMovieShow(Long movieShowId) {
        MovieShow movieShow = movieShowRepository.findById(movieShowId).orElseThrow(
                () -> new RuntimeException("MovieShow with id "+movieShowId+" not found")
        );
        movieShowRepository.delete(movieShow);
    }

    @Override
    public List<MovieShow> getMovieShowByCinemaHallId(Long cinemaHallId) {
       CinemaHall cinemaHall =  cinemaHallRepository.findById(cinemaHallId).orElseThrow(
                () -> new RuntimeException("Cinema hall with id "+cinemaHallId+" not found")
        );
       return movieShowRepository.findByCinemaHall(cinemaHall);
    }

    @Override
    public List<ShowSeat> getShowSeatByMovieShowId(Long movieShowId) {
        MovieShow movieShow = movieShowRepository.findById(movieShowId).orElseThrow(
                () -> new RuntimeException("MovieShow with id "+movieShowId+" not found")
        );
        return showSeatRepository.findAllByMovieShow(movieShow);
    }

    @Override
    public ShowSeatsDetails getShowSeatDetailsByMovieShowId(Long movieShowId) {
        MovieShow movieShow = movieShowRepository.findById(movieShowId).orElseThrow(
                () -> new RuntimeException("MovieShow with id "+movieShowId+" not found")
        );
        ShowSeatsDetails showSeatsDetails = new ShowSeatsDetails();
        showSeatsDetails.setNoOfPremiumSeats(movieShow.getCinemaHall().getNoOfPremiumSeats());
        showSeatsDetails.setNoOfVipSeats(movieShow.getCinemaHall().getNoOfVipSeats());
        showSeatsDetails.setNoOfRegularSeats(movieShow.getCinemaHall().getNoOfRegularSeats());
        showSeatsDetails.setVipSeatPrice(movieShow.getVipSeatPrice());
        showSeatsDetails.setRegularSeatPrice(movieShow.getRegularSeatPrice());
        showSeatsDetails.setPremiumSeatPrice(movieShow.getPremiumSeatPrice());
        return showSeatsDetails;
    }

    @Override
    public List<MovieShow> getMovieShowByMovieNameAndCity(Long movieShowId, String city) {
        List<MovieShow> movieShowList = movieShowRepository.searchMovieShow(movieShowId,city);
        if(movieShowList.isEmpty()){
            throw new RuntimeException("Their is no show of this movie in your select city "+city);
        }
        return movieShowList;
    }

    private MovieShow getMovieShow(CreateMovieShowRequest movieShowRequest,CinemaHall cinemaHall,Movie movie) {
        MovieShow movieShow = new MovieShow();
        movieShow.setCinemaHall(cinemaHall);
        movieShow.setMovie(movie);
        movieShow.setStartTime(movieShowRequest.getStartTime());
        movieShow.setEndTime(movieShowRequest.getStartTime().plusHours(movie.getDuration().getHour())
                .plusMinutes(movie.getDuration().getMinute()).plusMinutes(15));
        movieShow.setShowDate(movieShowRequest.getShowDate());
        movieShow.setLanguage(movieShowRequest.getLanguage());
        movieShow.setPremiumSeatPrice(movieShowRequest.getPremiumSeatPrice());
        movieShow.setRegularSeatPrice(movieShowRequest.getRegularSeatPrice());
        movieShow.setVipSeatPrice(movieShowRequest.getVipSeatPrice());
        movieShow.setScreenType(movieShowRequest.getScreenType());
        return movieShow;
    }
}
