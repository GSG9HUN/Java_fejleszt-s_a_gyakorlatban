package com.epam.training.ticketservice.commands;

import org.springframework.stereotype.Component;

@Component
public class CreateMovieCommand implements Command{

    @Override
    public String execute(String... parameter) {



        return parameter[0];
    }
}
