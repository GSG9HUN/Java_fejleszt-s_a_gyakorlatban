package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.Modell.Movie;

import java.util.List;

public interface MovieRepository {


    List<Movie> getAll();
    void saveMovie(Movie movie);

}
