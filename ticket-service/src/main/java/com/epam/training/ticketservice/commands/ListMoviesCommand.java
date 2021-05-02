package com.epam.training.ticketservice.commands;
import com.epam.training.ticketservice.Modell.Movie;
import com.epam.training.ticketservice.repository.impl.JpaMovieRepository;


import java.util.List;

public class ListMoviesCommand implements Command{

    JpaMovieRepository jpaMovieRepository;

    @Override
    public String execute(String... args) {

        List<Movie> movies =jpaMovieRepository.getAll();

        for (Movie movie:
             movies) {
            System.out.println(movie);
        }

        return null;
    }
}
