package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.Modell.Movie;
import com.epam.training.ticketservice.exceptions.CrudException;

import java.util.List;

public interface MovieRepository {


    List<Movie> getAll();
    void saveMovie(Movie movie);
    Movie updateMovie(String movieName, String movieCategory, int movieLength) throws CrudException;
    void deleteMovie(String movieName) throws CrudException;

}
