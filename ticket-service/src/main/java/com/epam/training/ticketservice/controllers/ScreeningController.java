package com.epam.training.ticketservice.controllers;

import com.epam.training.ticketservice.exceptions.CrudException;
import com.epam.training.ticketservice.exceptions.OverlappingException;
import com.epam.training.ticketservice.modell.AdminAccount;
import com.epam.training.ticketservice.modell.Movie;
import com.epam.training.ticketservice.modell.Room;
import com.epam.training.ticketservice.modell.Screening;
import com.epam.training.ticketservice.exceptions.EmptyListException;
import com.epam.training.ticketservice.services.ScreeningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.time.LocalDateTime;
import java.util.List;

@ShellComponent
public class ScreeningController {

    ScreeningService screeningService;

    @Autowired
    public ScreeningController(ScreeningService screeningService) {
        this.screeningService = screeningService;
    }


    @ShellMethod(value = "Creating screening", key = "create screening")
    public String createScreening(String movieName, String roomName, String screeningDate) {
        if (AdminAccount.isLogedIn()) {
            try {
                Movie movie = screeningService.findMovie(movieName);
                Room room = screeningService.findRoom(roomName);
                screeningService.createScreening(movieName, roomName, screeningDate);
                return "Screening created";
            } catch (CrudException | OverlappingException e) {
                return e.getMessage();
            }

        }
        return "You are not signed in";
    }

    @ShellMethod(value = "List all screening", key = "list screenings")
    public String listScreenings() {
        List<Screening> screeningList = null;
        try {
            screeningList = screeningService.listScreenings();
            for (Screening screening : screeningList) {
                Movie movie = null;
                movie = screeningService.findMovie(screening.getMovieName());
                LocalDateTime date = screening.getFilmStart();
                System.out.println(movie.getName() + " (" + movie.getCategory() + ", " + movie.getLength()
                        + " minutes), screened in room " + screening.getRoomName()
                        + ", at " + date.getYear()+"-"+date.getMonth()+"-"+date.getDayOfMonth()
                        +" "+date.getHour()+":"+date.getMinute());
            }

        } catch (EmptyListException | CrudException emptyList) {
            return emptyList.getMessage();
        }
        return null;
    }

    @ShellMethod(value = "Deleting screening", key = "delete screening")
    public String deleteScreening(String movieName, String roomName, String screeningDate) {
        if (AdminAccount.isLogedIn()) {
            try {
                screeningService.deleteScreening(movieName, roomName ,screeningDate);
            } catch (CrudException e) {
                return e.getMessage();
            }
            return "Screening deleted";
        }
        return "You are not signed in";
    }



}
