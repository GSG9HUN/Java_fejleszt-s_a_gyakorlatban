package com.epam.training.ticketservice.services;

import com.epam.training.ticketservice.repository.impl.JpaMovieRepository;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    private JpaMovieRepository jpaMovieRepository;

    public MovieService(JpaMovieRepository jpaMovieRepository) {
        this.jpaMovieRepository=jpaMovieRepository;
    }

    public String createMovie(String movieName, String movieType, String moviesLength){

        return "movie created";

    }

}
