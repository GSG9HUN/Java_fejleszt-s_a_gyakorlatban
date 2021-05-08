package com.epam.training.ticketservice.services;

import com.epam.training.ticketservice.exceptions.EmptyListException;
import com.epam.training.ticketservice.modell.Movie;
import com.epam.training.ticketservice.repository.impl.JpaMovieRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class MovieServiceTest {

    JpaMovieRepository jpaMovieRepository = Mockito.mock(JpaMovieRepository.class);

    @Test
    public void testListMoviesMethodWithNoMovies(){

        //given
        MovieService movieService = new MovieService(jpaMovieRepository);
        String result =null;
        //when
        try {
            movieService.listMovies();
        } catch (EmptyListException e) {
            result = e.getMessage();
        }
        //then
        assertEquals("Theres no movies at the moment",result);
    }

    @Test
    public void testListMoviesMethod() throws EmptyListException {

        //given
        MovieService movieService = new MovieService(jpaMovieRepository);
        when(jpaMovieRepository.getAll()).thenReturn(List.of(new Movie("film","drama",125)));
        //when
        List<Movie> movieList=movieService.listMovies();
        //then
        assertNotNull(movieList);
    }

}