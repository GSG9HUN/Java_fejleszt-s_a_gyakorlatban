package com.epam.training.ticketservice.repository.impl;

import com.epam.training.ticketservice.dataaccess.dao.RoomDao;
import com.epam.training.ticketservice.dataaccess.projection.RoomProjection;
import com.epam.training.ticketservice.modell.Movie;
import com.epam.training.ticketservice.modell.Room;
import com.epam.training.ticketservice.modell.Screening;
import com.epam.training.ticketservice.dataaccess.dao.MovieDao;
import com.epam.training.ticketservice.dataaccess.dao.ScreenDao;
import com.epam.training.ticketservice.dataaccess.projection.MovieProjection;
import com.epam.training.ticketservice.dataaccess.projection.ScreenProjection;
import com.epam.training.ticketservice.exceptions.CrudException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JpaScreeningRepository {

    private final ScreenDao screenDao;
    private final MovieDao movieDao;
    private final RoomDao roomDao;

    @Autowired
    public JpaScreeningRepository(ScreenDao screenDao, MovieDao movieDao, RoomDao roomDao) {
        this.screenDao = screenDao;
        this.movieDao = movieDao;
        this.roomDao = roomDao;
    }

    public List<Screening> findAllScreeningAtCurrentDate(LocalDateTime screeningDate, String roomName) {
        List<Screening> screenings = getAll()
                .stream()
                .filter(screening -> screening.getFilmStart().getYear() == screeningDate.getYear()
                        && screening.getFilmStart().getMonth() == screeningDate.getMonth()
                        && screening.getFilmStart().getDayOfMonth() == screeningDate.getDayOfMonth()
                        && screening.getRoomName().equals(roomName))
                .collect(Collectors.toList());
        return screenings;
    }

    public List<Screening> getAll() {
        return screenDao.findAll().stream()
                .map(this::mapScreening).collect(Collectors.toList());
    }

    public void save(Screening screening) {
        ScreenProjection screenProjection = mapScreening(screening);
        screenDao.save(screenProjection);
    }

    public void delete(String movieName, String roomName, String screeningDate) throws CrudException {
        Optional<ScreenProjection> screen = findScreenByName(movieName, roomName, screeningDate);
        if (screen.isPresent()) {
            screenDao.delete(screen.get());
            return;
        }
        throw new CrudException("Screening doesn't exist");
    }

    public Movie findMovie(String movieName) throws CrudException {
        Optional<MovieProjection> optionalMovieProjection = movieDao.findAll()
                .stream()
                .filter(movieProjection -> movieProjection.getName().equals(movieName)).findFirst();

        if (optionalMovieProjection.isEmpty()) {
            throw new CrudException("Movie doesn't exist");
        }
        MovieProjection movieProjection = optionalMovieProjection.get();

        return new Movie(movieProjection.getName(), movieProjection.getCategory(), movieProjection.getLength());
    }

    public Room findRoom(String roomName) throws CrudException {
        Optional<RoomProjection> optionalRoomProjection = roomDao.findAll()
                .stream()
                .filter(roomProjection -> roomProjection.getName().equals(roomName)).findFirst();

        if (optionalRoomProjection.isEmpty()) {
            throw new CrudException("Room doesn't exist");
        }
        RoomProjection roomProjection = optionalRoomProjection.get();

        return new Room(roomProjection.getName(), roomProjection.getRowNum(), roomProjection.getColNum());
    }

    private ScreenProjection mapScreening(Screening screening) {
        return new ScreenProjection(screening.getMovieName(), screening.getRoomName(),
                screening.getFilmStart(),screening.getFilmEnd());
    }

    private Screening mapScreening(ScreenProjection screenProjection) {
        return new Screening(screenProjection.getMovieName(),
                screenProjection.getRoomName(), screenProjection.getFilmStart(),screenProjection.getFilmEnd());
    }

    private List<Screening> mapScreenProjection(List<ScreenProjection> screenProjections) {
        return screenProjections.stream()
                .map(this::mapScreening)
                .collect(Collectors.toList());
    }

    private Optional<ScreenProjection> findScreenByName(String movieName, String roomName ,String screeningDate) {
        List<ScreenProjection> screenProjections = screenDao.findAll();
        LocalDateTime localDateTime = mapLocalDateTime(screeningDate);
        return screenProjections.stream()
                .filter(screenProjection -> screenProjection.getRoomName().equals(roomName)
                        && screenProjection.getMovieName().equals(movieName)
                        && screenProjection.getFilmStart().equals(localDateTime))
                .findFirst();
    }

    public LocalDateTime mapLocalDateTime(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(date, formatter);
    }
}