package com.movie.booking.webapp.service;

import com.movie.booking.webapp.model.Theater;
import com.movie.booking.webapp.repository.AddressRepository;
import com.movie.booking.webapp.repository.MovieRepository;
import com.movie.booking.webapp.repository.TheaterRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TheaterServiceImpl implements TheaterService {

    private TheaterRepository theaterRepository;

    @Override
    public Theater createTheater(Theater theater) {
        return theaterRepository.save(theater);
    }

    @Override
    public List<Theater> getAllTheaters() {
        return theaterRepository.findAll();
    }

    @Override
    public Theater updateTheater(Long id,Theater theater) {
         Theater exsitingTheater = theaterRepository.findById(id).orElseThrow(
                 () -> new RuntimeException("There is no theater with id " + id)
         );
         exsitingTheater.setName(theater.getName());
         exsitingTheater.setAddress(theater.getAddress());
         return theaterRepository.save(exsitingTheater);
    }

    @Override
    public void deleteTheater(Long id) {
        Theater exsitingTheater = theaterRepository.findById(id).orElseThrow(
                () -> new RuntimeException("There is no theater with id " + id)
        );
        theaterRepository.delete(exsitingTheater);
    }

    @Override
    public List<String> getAllCities() {
        return theaterRepository.getAllCities();
    }
}
