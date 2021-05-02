package com.epam.training.ticketservice.controllers;

import com.epam.training.ticketservice.commands.ListMoviesCommand;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class ListMoviesController {

    @ShellMethod(value = "List all movies",key = "list movies")
    public String list(){
        ListMoviesCommand listMoviesCommand = new ListMoviesCommand();
        return listMoviesCommand.execute();
    }
}
