package com.epam.training.ticketservice.controllers;

import com.epam.training.ticketservice.exceptions.CrudException;
import com.epam.training.ticketservice.exceptions.EmptyListException;
import com.epam.training.ticketservice.modell.AdminAccount;
import com.epam.training.ticketservice.modell.Movie;
import com.epam.training.ticketservice.services.MovieService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovieControllerTest {
    MovieService movieService = Mockito.mock(MovieService.class);
    MovieController movieController = new MovieController(movieService);

    @Test
    public void testCreateMovieButUserAreNotLoggedIn(){
        //given
        AdminAccount.setIsLogedIn(false);
        String result;
        //when
        result=movieController.createMovie("film","drama","154");
        //then
        assertEquals("You are not signed in",result);
    }

    @Test
    public void testCreateMovie(){
        //given
        AdminAccount.setIsLogedIn(true);
        String result;
        //when
        result=movieController.createMovie("film","drama","154");
        //then
        assertEquals("Movie created",result);
    }

    @Test
    public void testListMoviesButNoMoviesInTheDao() throws EmptyListException {
        //given
        String result=null;
        doThrow(new EmptyListException("Theres no movies at the moment")).when(movieService).listMovies();
        //when
        result =movieController.list();
        //then
        assertEquals("Theres no movies at the moment",result);
    }
    @Test
    public void testListMovies() throws EmptyListException {
        //given
        String result;
        when(movieService.listMovies()).thenReturn(List.of(new Movie("Film","Action",123)));
        //when
        result =movieController.list();
        //then
        assertNull(result);
    }

    @Test
    public  void testUpdateMovieMethodButUserAreNotLoggedIn(){
        //given
        String result;
        AdminAccount.setIsLogedIn(false);
        //when
        result=movieController.updateMovie("Film","Action","123");
        //then
        assertEquals("You are not signed in",result);
    }

    @Test
    public  void testUpdateMovieMethodButNonExistentMovie() throws CrudException {
        //given
        String result;
        AdminAccount.setIsLogedIn(true);
        doThrow(new CrudException("Movie doesn't exist")).when(movieService).updateMovie("Film","Sci-fi","156");
        //when
        result=movieController.updateMovie("Film","Sci-fi","156");
        //then
        assertEquals("Movie doesn't exist",result);
    }

    @Test
    public  void testUpdateMovieMethod() throws CrudException {
        //given
        String result;
        AdminAccount.setIsLogedIn(true);
        //when
        result=movieController.updateMovie("Film","Action","123");
        //then
        verify(movieService,times(1)).updateMovie("Film","Action","123");
    }

    @Test
    public void testDeleteMovieMethodButUserAreNotLoggedIn(){
        //given
        AdminAccount.setIsLogedIn(false);
        String result;
        //when
        result=movieController.deleteMovie("Film");
        //then
        assertEquals("You are not signed in",result);
    }

    @Test
    public void testDeleteMovieMethodButNonExistentMovie() throws CrudException {
        //given
        AdminAccount.setIsLogedIn(true);
        String result;
        doThrow(new CrudException("Movie doesn't exist")).when(movieService).deleteMovie("Film");
        //when
        result=movieController.deleteMovie("Film");
        //then
        assertEquals("Movie doesn't exist",result);
    }

    @Test
    public void testDeleteMovieMethod() throws CrudException {
        //given
        AdminAccount.setIsLogedIn(true);
        String result;
        doNothing().when(movieService).deleteMovie("Film");
        //when
        result=movieController.deleteMovie("Film");
        //then
        assertEquals("Movie deleted",result);
    }



}