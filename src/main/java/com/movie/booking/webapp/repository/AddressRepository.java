package com.movie.booking.webapp.repository;

import com.movie.booking.webapp.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    @Query("select distinct a.city from Address a,Theater t where a.id = t.address.id")
    List<String> getAllCities();
}
