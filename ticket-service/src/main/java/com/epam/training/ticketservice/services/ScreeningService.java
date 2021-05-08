package com.epam.training.ticketservice.services;

import com.epam.training.ticketservice.exceptions.OverlappingException;
import com.epam.training.ticketservice.modell.Movie;
import com.epam.training.ticketservice.modell.Room;
import com.epam.training.ticketservice.modell.Screening;
import com.epam.training.ticketservice.exceptions.CrudException;
import com.epam.training.ticketservice.exceptions.EmptyListException;
import com.epam.training.ticketservice.repository.impl.JpaScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScreeningService {

    private JpaScreeningRepository jpaScrreningRepository;

    @Autowired
    public ScreeningService(JpaScreeningRepository jpaScrreningRepository) {
        this.jpaScrreningRepository = jpaScrreningRepository;
    }

    public void createScreening(String movieName, String roomName, String screeningDate)
            throws CrudException, OverlappingException {
        LocalDateTime localDate = jpaScrreningRepository.mapLocalDateTime(screeningDate);
        Movie movie = findMovie(movieName);
        List<Screening> screeningList = jpaScrreningRepository.findAllScreeningAtCurrentDate(localDate, roomName);
        Screening newScreening =
                new Screening(movieName, roomName, localDate, localDate.plusMinutes(movie.getLength()));
        if (screeningList.isEmpty()) {
            jpaScrreningRepository.save(newScreening);
            return;
        }
        for (Screening screening : screeningList) {
            if (haveOverlapping(screening, newScreening)) {
                throw new OverlappingException("There is an overlapping screening");
            }
            if (isThereABreak(screening, newScreening)) {
                throw new OverlappingException("This would start in the break period "
                        + "after another screening in this room");
            }
        }
        jpaScrreningRepository.save(newScreening);
    }

    public boolean isThereABreak(Screening screening, Screening newScreening) {
        LocalDateTime filmStartDate = screening.getFilmStart();
        LocalDateTime filmEndDate = screening.getFilmEnd();
        LocalDateTime newFilmStartDate = newScreening.getFilmStart();
        LocalDateTime newFilmEndDate = newScreening.getFilmEnd();

        if (filmEndDate.isBefore(newFilmStartDate)
                && newFilmStartDate.isBefore(filmEndDate.plusMinutes(10))) {
            return true;
        }

        if (newFilmEndDate.isBefore(filmStartDate)
                && filmStartDate.isBefore(newFilmEndDate.plusMinutes(10))) {
            return true;
        }

        return false;
    }

    public boolean haveOverlapping(Screening screening, Screening newScreening) {
        LocalDateTime filmStartDate = screening.getFilmStart();
        LocalDateTime filmEndDate = screening.getFilmEnd();
        LocalDateTime newFilmStartDate = newScreening.getFilmStart();
        LocalDateTime newFilmEndDate = newScreening.getFilmEnd();
        if (filmStartDate.equals(newFilmStartDate)
                || filmEndDate.equals(newFilmEndDate)) {
            return true;
        }

        if ((newFilmStartDate.isBefore(filmStartDate)
                && newFilmEndDate.isAfter(filmEndDate))
                || (filmStartDate.isBefore(newFilmStartDate)
                && filmEndDate.isAfter(newFilmEndDate))) {
            return true;
        }

        if (newFilmStartDate.isBefore(filmStartDate)
                && newFilmEndDate.isAfter(filmStartDate)) {
            return true;
        }

        if (filmStartDate.isBefore(newFilmStartDate)
                && newFilmStartDate.isBefore(filmEndDate)) {
            return true;
        }
        return false;
    }

    public List<Screening> listScreenings() throws EmptyListException {
        List<Screening> screening = jpaScrreningRepository.getAll();
        if (screening.isEmpty()) {
            throw new EmptyListException("Theres no screening at the moment");
        }
        return screening;
    }

    public void deleteScreening(String movieName, String roomName, String screeningDate) throws CrudException {
        jpaScrreningRepository.delete(movieName, roomName,screeningDate);
    }

    public Movie findMovie(String movieName) throws CrudException {
        return jpaScrreningRepository.findMovie(movieName);
    }

    public Room findRoom(String roomName) throws CrudException {
        return jpaScrreningRepository.findRoom(roomName);
    }

}
