package com.epam.training.ticketservice.dataaccess.projection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Entity
public class ScreenProjection {

    @Id
    @GeneratedValue
    private UUID id;
    private String movieName;
    private String roomName;
    private LocalDateTime filmStart;
    private LocalDateTime filmEnd;



    public ScreenProjection(String movieName, String roomName, LocalDateTime filmStart, LocalDateTime filmEnd) {
        this.movieName = movieName;
        this.roomName = roomName;
        this.filmStart = filmStart;
        this.filmEnd = filmEnd;
    }

    protected ScreenProjection() {
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public LocalDateTime getFilmStart() {
        return filmStart;
    }

    public void setFilmStart(LocalDateTime filmStart) {
        this.filmStart = filmStart;
    }

    public LocalDateTime getFilmEnd() {
        return filmEnd;
    }

    public void setFilmEnd(LocalDateTime filmEnd) {
        this.filmEnd = filmEnd;
    }
}
