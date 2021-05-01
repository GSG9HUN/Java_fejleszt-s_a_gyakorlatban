package com.epam.training.ticketservice.controllers;

import com.epam.training.ticketservice.commands.CreateMovieCommand;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class CreateMovieController {



    @ShellMethod(value = "Create movie", key = "create movie")
    public String createMovie(String movieName, String movieType, String moviesLength){
        CreateMovieCommand createMovieCommand = new CreateMovieCommand();
        return createMovieCommand.execute(movieName,movieType,moviesLength);

    }
}
