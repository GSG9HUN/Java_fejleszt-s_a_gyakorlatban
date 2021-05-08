package com.epam.training.ticketservice.modell;

import java.time.LocalDateTime;

public class Screening {

    private String movieName;
    private String roomName;
    private LocalDateTime filmStart;
    private LocalDateTime filmEnd;

    public Screening(String movieName, String roomName, LocalDateTime filmStart,LocalDateTime filmEnd) {
        this.movieName = movieName;
        this.roomName = roomName;
        this.filmStart = filmStart;
        this.filmEnd = filmEnd;
    }

    public String getMovieName() {
        return movieName;
    }

    public String getRoomName() {
        return roomName;
    }

    public LocalDateTime getFilmStart() {
        return filmStart;
    }

    public LocalDateTime getFilmEnd() {
        return filmEnd;
    }
}
