package com.epam.training.ticketservice.controllers;
import com.epam.training.ticketservice.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class CreateMovieController {

    MovieService movieService;

    @Autowired
    CreateMovieController(MovieService movieService){
        this.movieService=movieService;
    }


    @ShellMethod(value = "Create movie", key = "create movie")
    public String createMovie(String movieName, String movieType, String moviesLength){

        return movieService.createMovie(movieName,movieType,moviesLength);

    }
}
