package com.movie.booking.webapp.service;

import com.movie.booking.webapp.jwt.JwtHelper;
import com.movie.booking.webapp.model.Booking;
import com.movie.booking.webapp.model.User;
import com.movie.booking.webapp.repository.BookingRepository;
import com.movie.booking.webapp.repository.ShowSeatRepository;
import com.movie.booking.webapp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private BookingRepository bookingRepository;
    private JwtHelper jwtHelper;

    @Override
    public User getUserByToken(String token) {
        token = token.substring(7);
        String username = jwtHelper.getUsernameFromToken(token);
        User user = userRepository.findByEmail(username).orElseThrow(
                () -> new RuntimeException("User not found")
        );
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return user;
    }

    @Override
    public List<Booking> getAllBookings(String token) {
        token = token.substring(7);
        String username = jwtHelper.getUsernameFromToken(token);
        User user = userRepository.findByEmail(username).orElseThrow(
                () -> new RuntimeException("User not found")
        );
        return bookingRepository.findByUser(user);
    }

    @Override
    public List<User> getAllUsers() {
       List<User> users = userRepository.findAll();
       for(int i = 0; i < users.size(); i++){
           users.get(i).setPassword(null);
       }
       return users;
    }
}
