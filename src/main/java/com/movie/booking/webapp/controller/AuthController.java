package com.movie.booking.webapp.controller;


import com.movie.booking.webapp.jwt.JwtHelper;
import com.movie.booking.webapp.model.Role;
import com.movie.booking.webapp.model.User;
import com.movie.booking.webapp.repository.UserRepository;
import com.movie.booking.webapp.request.LoginRequest;
import com.movie.booking.webapp.response.JwtResponse;
import com.movie.booking.webapp.service.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@RestController
@AllArgsConstructor
public class AuthController {

    private PasswordEncoder passwordEncoder;

    private UserRepository userRepository;

    private DaoAuthenticationProvider authProvider;

    private CustomUserDetailsService userDetailsService;

    private JwtHelper jwtHelper;

    @PostMapping("public/register")
    public ResponseEntity<JwtResponse> createUser(@RequestBody User user) {
            if(userRepository.findByEmail(user.getEmail()).isPresent()) {
                throw new BadCredentialsException("Email already associated with another account");
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User savedUser = userRepository.save(user);
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(savedUser.getRole().toString()));
            String token = jwtHelper.generateToken(new org.springframework.security.core.userdetails.User(
                    savedUser.getEmail(),
                    savedUser.getPassword(),
                    authorities
            ));
            JwtResponse jwtResponse = new JwtResponse(token,savedUser.getRole().toString());
            return new ResponseEntity<>(jwtResponse, HttpStatus.CREATED);
    }

    @PostMapping("public/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request) {

        this.doAuthenticate(request.getEmail(), request.getPassword());


        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = this.jwtHelper.generateToken(userDetails);

        Collection<? extends GrantedAuthority> authorities =  userDetails.getAuthorities();
        String role = null;
        for(GrantedAuthority authority : authorities) {
            role = authority.getAuthority();
        }
        JwtResponse response = new JwtResponse(token,role);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void doAuthenticate(String email, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            authProvider.authenticate(authentication);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }

    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private String exceptionHandler(BadCredentialsException exception) {
        return exception.getMessage();
    }
}
