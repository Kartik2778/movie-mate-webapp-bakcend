package com.movie.booking.webapp.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movie_id;

    private String title;

    @Lob
    private String description;

    private String releaseDate;

    private LocalTime duration;

    private String photo;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> languages;

    private String type;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "movie")
    @JsonIgnore
    private List<MovieShow> movieShows = new ArrayList<>();
}
