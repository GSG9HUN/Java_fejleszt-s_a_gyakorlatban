package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.Modell.AdminAccount;
import com.epam.training.ticketservice.Modell.Movie;
import org.springframework.stereotype.Component;

@Component
public class CreateMovieCommand implements Command{


    @Override
    public String execute(String... parameter) {

        if(AdminAccount.isLogedIn()){
            String name = parameter[0];
            String category = parameter[1];
            int length = Integer.parseInt(parameter[2]);
            Movie movie = new Movie(name,category,length);
            return "Movie created";
        }

        return "You have to sign in";
    }
}
