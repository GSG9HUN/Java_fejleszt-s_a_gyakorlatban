package com.epam.training.ticketservice.services;

import com.epam.training.ticketservice.exceptions.CrudException;
import com.epam.training.ticketservice.exceptions.EmptyListException;
import com.epam.training.ticketservice.exceptions.OverlappingException;
import com.epam.training.ticketservice.modell.Movie;
import com.epam.training.ticketservice.modell.Screening;
import com.epam.training.ticketservice.repository.impl.JpaScreeningRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

class ScreeningServiceTest {

    JpaScreeningRepository jpaScrreningRepository = Mockito.mock(JpaScreeningRepository.class);


    @Test
    public void testListScreeningMethodWithoutScreenings(){
        //given
        String result = null;
        ScreeningService screeningService = new ScreeningService(jpaScrreningRepository);
        //when
        try {
            screeningService.listScreenings();
        } catch (EmptyListException e) {
            result = e.getMessage();
        }
        //then
        assertEquals("There are no screenings",result);
    }


    @Test
    public void testListScreeningMethod() throws EmptyListException {
        //given
        ScreeningService screeningService = new ScreeningService(jpaScrreningRepository);
        String date = "2012-11-30 16:00";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime filmStart = LocalDateTime.parse(date,dateTimeFormatter);
        LocalDateTime filmEnd = filmStart.plusMinutes(120);
        when(jpaScrreningRepository.getAll()).thenReturn(List.of(new Screening("film","Terem",filmStart,filmEnd)));
        //when
        List<Screening> screeningList =screeningService.listScreenings();
        //then
        assertNotNull(screeningList);
    }


    @Test
    public void testHaveOverlappingMethodSameStartAndEndDate(){
        //given
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime filmStart =LocalDateTime.parse("2012-11-30 15:00",dateTimeFormatter);
        LocalDateTime filmEnd =LocalDateTime.parse("2012-11-30 16:00",dateTimeFormatter);
        Screening screening = new Screening("film","terem", filmStart,filmEnd);
        Screening newScreening = new Screening("film","terem", filmStart,filmEnd);
        ScreeningService screeningService = new ScreeningService(jpaScrreningRepository);
        //when
        boolean result = screeningService.haveOverlapping(screening,newScreening);
        //then
        assertTrue(result);
    }

    @Test
    public void testHaveOverlappingMethodNewScreeningOverlappingTheOldOneStartDate(){
        //given
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime filmStart =LocalDateTime.parse("2012-11-30 15:00",dateTimeFormatter);
        LocalDateTime filmEnd =LocalDateTime.parse("2012-11-30 16:00",dateTimeFormatter);
        LocalDateTime newFilmStart =LocalDateTime.parse("2012-11-30 14:00",dateTimeFormatter);
        LocalDateTime newFilmEnd =LocalDateTime.parse("2012-11-30 15:30",dateTimeFormatter);
        Screening screening = new Screening("film","terem", filmStart,filmEnd);
        Screening newScreening = new Screening("film","terem", newFilmStart,newFilmEnd);
        ScreeningService screeningService = new ScreeningService(jpaScrreningRepository);
        //when
        boolean result = screeningService.haveOverlapping(screening,newScreening);
        //then
        assertTrue(result);
    }

    @Test
    public void testHaveOverlappingMethodNewScreeningOverlappingTheOldOneEndDate(){
        //given
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime filmStart =LocalDateTime.parse("2012-11-30 15:00",dateTimeFormatter);
        LocalDateTime filmEnd =LocalDateTime.parse("2012-11-30 16:00",dateTimeFormatter);
        LocalDateTime newFilmStart =LocalDateTime.parse("2012-11-30 15:30",dateTimeFormatter);
        LocalDateTime newFilmEnd =LocalDateTime.parse("2012-11-30 16:30",dateTimeFormatter);
        Screening screening = new Screening("film","terem", filmStart,filmEnd);
        Screening newScreening = new Screening("film","terem", newFilmStart,newFilmEnd);
        ScreeningService screeningService = new ScreeningService(jpaScrreningRepository);
        //when
        boolean result = screeningService.haveOverlapping(screening,newScreening);
        //then
        assertTrue(result);
    }

    @Test
    public void testHaveOverlappingMethodAndReturnFalse(){
        //given
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime filmStart =LocalDateTime.parse("2012-11-30 15:00",dateTimeFormatter);
        LocalDateTime filmEnd =LocalDateTime.parse("2012-11-30 16:00",dateTimeFormatter);
        LocalDateTime newFilmStart =LocalDateTime.parse("2012-11-30 17:30",dateTimeFormatter);
        LocalDateTime newFilmEnd =LocalDateTime.parse("2012-11-30 18:30",dateTimeFormatter);
        Screening screening = new Screening("film","terem", filmStart,filmEnd);
        Screening newScreening = new Screening("film","terem", newFilmStart,newFilmEnd);
        ScreeningService screeningService = new ScreeningService(jpaScrreningRepository);
        //when
        boolean result = screeningService.haveOverlapping(screening,newScreening);
        //then
        assertFalse(result);
    }


    @Test
    public void testIsThereABreakMethodWhenTheNewScreeningEndsBeforeTheOldScreeningStartingDateLessThan10MinuteAfter(){
        //given
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime filmStart =LocalDateTime.parse("2012-11-30 15:00",dateTimeFormatter);
        LocalDateTime filmEnd =LocalDateTime.parse("2012-11-30 16:00",dateTimeFormatter);
        LocalDateTime newFilmStart =LocalDateTime.parse("2012-11-30 14:00",dateTimeFormatter);
        LocalDateTime newFilmEnd =LocalDateTime.parse("2012-11-30 14:55",dateTimeFormatter);
        Screening screening = new Screening("film","terem", filmStart,filmEnd);
        Screening newScreening = new Screening("film","terem", newFilmStart,newFilmEnd);
        ScreeningService screeningService = new ScreeningService(jpaScrreningRepository);
        //when
        boolean result = screeningService.isThereABreak(screening,newScreening);
        //then
        assertTrue(result);
    }

    @Test
    public void testIsThereABreakMethodWhenTheNewScreeningStartingAfterTheOldOneButLessThan10MinuteAfter(){
        //given
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime filmStart =LocalDateTime.parse("2012-11-30 15:00",dateTimeFormatter);
        LocalDateTime filmEnd =LocalDateTime.parse("2012-11-30 16:00",dateTimeFormatter);
        LocalDateTime newFilmStart =LocalDateTime.parse("2012-11-30 16:05",dateTimeFormatter);
        LocalDateTime newFilmEnd =LocalDateTime.parse("2012-11-30 16:55",dateTimeFormatter);
        Screening screening = new Screening("film","terem", filmStart,filmEnd);
        Screening newScreening = new Screening("film","terem", newFilmStart,newFilmEnd);
        ScreeningService screeningService = new ScreeningService(jpaScrreningRepository);
        //when
        boolean result = screeningService.isThereABreak(screening,newScreening);
        //then
        assertTrue(result);
    }

    @Test
    public void testIsThereABreakMethodWhenTheNewScreeningStartingAfterTheOldOneAfter10Minutes(){
        //given
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime filmStart =LocalDateTime.parse("2012-11-30 15:00",dateTimeFormatter);
        LocalDateTime filmEnd =LocalDateTime.parse("2012-11-30 16:00",dateTimeFormatter);
        LocalDateTime newFilmStart =LocalDateTime.parse("2012-11-30 16:10",dateTimeFormatter);
        LocalDateTime newFilmEnd =LocalDateTime.parse("2012-11-30 18:00",dateTimeFormatter);
        Screening screening = new Screening("film","terem", filmStart,filmEnd);
        Screening newScreening = new Screening("film","terem", newFilmStart,newFilmEnd);
        ScreeningService screeningService = new ScreeningService(jpaScrreningRepository);
        //when
        boolean result = screeningService.isThereABreak(screening,newScreening);
        //then
        assertFalse(result);
    }

}