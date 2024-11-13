package com.movie.booking.webapp.controller;

import com.movie.booking.webapp.model.User;
import com.movie.booking.webapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("admin")
@AllArgsConstructor
public class AdminController {

    private UserService userService;

    @GetMapping("users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
