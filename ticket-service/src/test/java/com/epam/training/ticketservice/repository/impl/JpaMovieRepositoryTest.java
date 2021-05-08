package com.epam.training.ticketservice.repository.impl;

import com.epam.training.ticketservice.dataaccess.dao.MovieDao;
import com.epam.training.ticketservice.dataaccess.projection.MovieProjection;
import com.epam.training.ticketservice.exceptions.CrudException;
import com.epam.training.ticketservice.modell.Movie;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JpaMovieRepositoryTest {

    @Test
    public void testUpdateMethodWithNonExistentMovie(){
        //given
        String movieName ="Film";
        String movieCategory = "film_category";
        String movieLength = "125";
        MovieDao movieDao = Mockito.mock(MovieDao.class);
        JpaMovieRepository jpaMovieRepository = new JpaMovieRepository(movieDao);
        String result = null;
        //when
        try {
           jpaMovieRepository.update(movieName,movieCategory,movieLength);
        } catch (CrudException e) {
             result = e.getMessage();
        }
        //then


        assertEquals("Movie doesn't exist",result);
    }


    @Test
    public void testUpdateMethodWithExistentMovie() throws CrudException {
        //given
        String movieName ="film";
        String movieCategory = "film_category";
        String movieLength = "125";
        MovieDao movieDao = Mockito.mock(MovieDao.class);
        JpaMovieRepository jpaMovieRepository = new JpaMovieRepository(movieDao);
        when(movieDao.findAll()).thenReturn(List.of(new MovieProjection("film","drama",123),
                new MovieProjection("film2","Action",156)));
        Optional<MovieProjection> movie = movieDao.findAll()
                .stream()
                .filter(movieProjection -> movieProjection.getName().equals("film"))
                .findFirst();
        //when
        jpaMovieRepository.update(movieName,movieCategory,movieLength);
        //then
        verify(movieDao,times(1)).save(movie.get());


    }

    @Test
    public void testDeleteMethodWithNonExistentMovie() throws CrudException {
        //given
        String movieName ="film";
        String movieCategory = "film_category";
        String movieLength = "125";
        MovieDao movieDao = Mockito.mock(MovieDao.class);
        JpaMovieRepository jpaMovieRepository = new JpaMovieRepository(movieDao);
        String result =null;
        //when
        try{
            jpaMovieRepository.delete(movieName);
        }catch (CrudException e){
            result = e.getMessage();
        }
        //then
        assertEquals("Movie doesn't exist",result);
    }

    @Test
    public void testDeleteMethodWithExistentMovie() throws CrudException {
        //given
        String movieName ="film";
        MovieDao movieDao = Mockito.mock(MovieDao.class);
        JpaMovieRepository jpaMovieRepository = new JpaMovieRepository(movieDao);
        when(movieDao.findAll()).thenReturn(List.of(new MovieProjection("film","drama",123),
                new MovieProjection("film2","Action",156)));
        Optional<MovieProjection> movie = movieDao.findAll()
                .stream()
                .filter(movieProjection -> movieProjection.getName().equals("film"))
                .findFirst();

        //when
        jpaMovieRepository.delete(movieName);

        //then
        verify(movieDao,times(1)).delete(movie.get());
    }

}