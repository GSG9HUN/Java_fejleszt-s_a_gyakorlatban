package com.epam.training.ticketservice.repository.impl;

import com.epam.training.ticketservice.dataaccess.dao.MovieDao;
import com.epam.training.ticketservice.dataaccess.dao.RoomDao;
import com.epam.training.ticketservice.dataaccess.dao.ScreenDao;
import com.epam.training.ticketservice.dataaccess.projection.MovieProjection;
import com.epam.training.ticketservice.dataaccess.projection.RoomProjection;
import com.epam.training.ticketservice.dataaccess.projection.ScreenProjection;
import com.epam.training.ticketservice.exceptions.CrudException;
import com.epam.training.ticketservice.modell.Movie;
import com.epam.training.ticketservice.modell.Room;
import com.epam.training.ticketservice.modell.Screening;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class JpaScreeningRepositoryTest {

    @Test
    public void TestDeleteMethodWithNonExistentScreening(){
        ScreenDao screenDao = Mockito.mock(ScreenDao.class);
        MovieDao movieDao = Mockito.mock(MovieDao.class);
        RoomDao roomDao = Mockito.mock(RoomDao.class);
        JpaScreeningRepository jpaScreeningRepository =
                new JpaScreeningRepository(screenDao,movieDao,roomDao);
        String result = null;

        //when
        try {
            jpaScreeningRepository.delete("film","terem", "2012-11-30 16:30");
        } catch (CrudException e) {
            result =e.getMessage();
        }
        //then
        assertEquals("Screening doesn't exist",result);
    }

    @Test
    public void TestDeleteMethodWithExistentScreening() throws CrudException {
        ScreenDao screenDao = Mockito.mock(ScreenDao.class);
        MovieDao movieDao = Mockito.mock(MovieDao.class);
        RoomDao roomDao = Mockito.mock(RoomDao.class);
        DateTimeFormatter dateTimeFormatter =DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startFilm = LocalDateTime.parse("2012-11-30 16:30",dateTimeFormatter);

        JpaScreeningRepository jpaScreeningRepository =
                new JpaScreeningRepository(screenDao,movieDao,roomDao);

        when(screenDao.findAll()).thenReturn(List.of(new ScreenProjection("film","terem",startFilm,startFilm.plusMinutes(120)),
                (new ScreenProjection("film1","terem1",startFilm,startFilm.plusMinutes(120)))));
        Optional<ScreenProjection> screening = screenDao.findAll()
                .stream()
                .filter(screenProjection -> screenProjection.getMovieName().equals("film")
                        && screenProjection.getRoomName().equals("terem"))
                .findFirst();
        //when
        jpaScreeningRepository.delete("film","terem","2012-11-30 16:30");
        //then
        verify(screenDao).delete(screening.get());
    }

    @Test
    public void TestFindMovieMethodWithExistingMovie() throws CrudException {
        //given
        ScreenDao screenDao = Mockito.mock(ScreenDao.class);
        MovieDao movieDao = Mockito.mock(MovieDao.class);
        RoomDao roomDao = Mockito.mock(RoomDao.class);
        JpaScreeningRepository jpaScreeningRepository =
                new JpaScreeningRepository(screenDao,movieDao,roomDao);
        when(movieDao.findAll()).thenReturn(List.of(new MovieProjection("film","drama",123)));
        //when
        Movie movie =jpaScreeningRepository.findMovie("film");

        //then
        assertNotNull(movie);
    }

    @Test
    public void TestFindMovieMethodWithNonExistingMovie() {
        //given
        ScreenDao screenDao = Mockito.mock(ScreenDao.class);
        MovieDao movieDao = Mockito.mock(MovieDao.class);
        RoomDao roomDao = Mockito.mock(RoomDao.class);
        JpaScreeningRepository jpaScreeningRepository =
                new JpaScreeningRepository(screenDao,movieDao,roomDao);
        String result=null;
        //when
        try {
            jpaScreeningRepository.findMovie("film");
        } catch (CrudException e) {
            result = e.getMessage();
        }

        //then
        assertEquals("Movie doesn't exist",result);
    }

    @Test
    public void TestFindRoomMethodWithExistingRoom() throws CrudException {
        //given
        ScreenDao screenDao = Mockito.mock(ScreenDao.class);
        MovieDao movieDao = Mockito.mock(MovieDao.class);
        RoomDao roomDao = Mockito.mock(RoomDao.class);
        JpaScreeningRepository jpaScreeningRepository =
                new JpaScreeningRepository(screenDao,movieDao,roomDao);
        when(roomDao.findAll()).thenReturn(List.of(new RoomProjection("terem",2,2)));
        //when
        Room room =jpaScreeningRepository.findRoom("terem");

        //then
        assertNotNull(room);
    }

    @Test
    public void TestFindRoomMethodWithNonExistingRoom() {
        //given
        ScreenDao screenDao = Mockito.mock(ScreenDao.class);
        MovieDao movieDao = Mockito.mock(MovieDao.class);
        RoomDao roomDao = Mockito.mock(RoomDao.class);
        JpaScreeningRepository jpaScreeningRepository =
                new JpaScreeningRepository(screenDao,movieDao,roomDao);
        String result=null;
        //when
        try {
            jpaScreeningRepository.findRoom("terem");
        } catch (CrudException e) {
            result = e.getMessage();
        }

        //then
        assertEquals("Room doesn't exist",result);
    }

}