package com.epam.training.ticketservice.controllers;

import com.epam.training.ticketservice.exceptions.CrudException;
import com.epam.training.ticketservice.exceptions.EmptyListException;
import com.epam.training.ticketservice.exceptions.OverlappingException;
import com.epam.training.ticketservice.modell.AdminAccount;
import com.epam.training.ticketservice.modell.Movie;
import com.epam.training.ticketservice.modell.Screening;
import com.epam.training.ticketservice.services.ScreeningService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ScreeningControllerTest {
    ScreeningService screeningService = Mockito.mock(ScreeningService.class);
    ScreeningController screeningController = new ScreeningController(screeningService);


    @Test
    public void testCreateScreeningMethodButUserAreNotLoggedIn(){
        //given
        AdminAccount.setIsLogedIn(false);
        String result;
        //when
        result = screeningController.createScreening("film","terem","2012-11-30 16:00");
        //then
        assertEquals("You are not signed in",result);
    }

    @Test
    public void testCreateScreeningMethodButMovieDoesntExist() throws CrudException {
        //given
        AdminAccount.setIsLogedIn(true);
        doThrow(new CrudException("Movie doesn't exist")).when(screeningService).findMovie("film");
        String result;
        //when
        result = screeningController.createScreening("film","terem","2012-11-30 16:00");
        //then
        assertEquals("Movie doesn't exist",result);
    }

    @Test
    public void testCreateScreeningMethodButRoomDoesntExist() throws CrudException {
        //given
        AdminAccount.setIsLogedIn(true);
        doThrow(new CrudException("Room doesn't exist")).when(screeningService).findRoom("terem");
        String result;
        //when
        result = screeningController.createScreening("film","terem","2012-11-30 16:00");
        //then
        assertEquals("Room doesn't exist",result);
    }

    @Test
    public void testCreateScreeningMethodButItIsOverlapping() throws OverlappingException, CrudException {
        //given
        AdminAccount.setIsLogedIn(true);
        doThrow(new OverlappingException("There is an overlapping screening")).when(screeningService).createScreening("film","terem","2012-11-30 16:00");
        String result;
        //when
        result = screeningController.createScreening("film","terem","2012-11-30 16:00");
        //then
        assertEquals("There is an overlapping screening",result);
    }

    @Test
    public void testCreateScreeningMethodButIsIsInABreakTime() throws OverlappingException, CrudException {
        //given
        AdminAccount.setIsLogedIn(true);
        doThrow(new OverlappingException("This would start in the break period after another screening in this room"))
                .when(screeningService).createScreening("film","terem","2012-11-30 16:00");
        String result;
        //when
        result = screeningController.createScreening("film","terem","2012-11-30 16:00");
        //then
        assertEquals("This would start in the break period after another screening in this room",result);
    }

    @Test
    public void testCreateScreeningMethodAndScreeningCreated() throws OverlappingException, CrudException {
        //given
        AdminAccount.setIsLogedIn(true);
        doNothing().when(screeningService).createScreening("film","terem","2012-11-30 16:00");
        String result;
        //when
        result = screeningController.createScreening("film","terem","2012-11-30 16:00");
        //then
        assertEquals("Screening created",result);
    }



    @Test
    public void testListScreeningMethodButTheresNoScreeningNow() throws EmptyListException {
        //given
        String result;
        doThrow(new EmptyListException("There are no screening at the moment")).when(screeningService).listScreenings();
        //when
        result = screeningController.listScreenings();
        //then
        assertEquals("There are no screening at the moment",result);
    }

    @Test
    public void testListScreeningMethod() throws EmptyListException, CrudException {
        //given
        String result;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime date = LocalDateTime.parse("2012-11-30 16:00",dateTimeFormatter);
        when(screeningService.listScreenings()).thenReturn(List.of(new Screening("Film","Terem",date,date.plusMinutes(120))));
        when(screeningService.findMovie("Film")).thenReturn(new Movie("Film","action",120));
        //when
        result = screeningController.listScreenings();
        //then
        assertNull(result);
    }

    @Test
    public void testDeleteScreeningMethodWithNonExistentScreening() throws CrudException {
        //given
        AdminAccount.setIsLogedIn(true);
        String result;
        Mockito.doThrow(new CrudException("Screening doesn't exist")).when(screeningService).deleteScreening("Film","Terem","2012-11-30 16:30");
        //when
        result =screeningController.deleteScreening("Film","Terem","2012-11-30 16:30");
        //then
        assertEquals("Screening doesn't exist",result);
    }

    @Test
    public void testDeleteScreeningMethodWithExistentScreening() throws CrudException {
        //given
        AdminAccount.setIsLogedIn(true);
        String result;
        doNothing().when(screeningService).deleteScreening("Film","Terem","2012-11-30 16:30");
        //when
        result =screeningController.deleteScreening("Film","Terem","2012-11-30 16:30");
        //then
        assertEquals("Screening deleted",result);
    }

    @Test
    public void testDeleteScreeningButUserAreNotLoggedIn(){
        //given
        AdminAccount.setIsLogedIn(false);
        String result;
        //when
        result =screeningController.deleteScreening("Film","Terem","2012-11-30 16:30");
        //then
        assertEquals("You are not signed in",result);
    }

}