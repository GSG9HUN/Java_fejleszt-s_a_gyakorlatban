package com.epam.training.ticketservice.controllers;

import com.epam.training.ticketservice.exceptions.BookException;
import com.epam.training.ticketservice.exceptions.CrudException;
import com.epam.training.ticketservice.modell.Book;
import com.epam.training.ticketservice.modell.Seats;
import com.epam.training.ticketservice.services.BookService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@ShellComponent
public class BookingController {

    private final BookService bookService;

    BookingController(BookService bookService) {
        this.bookService = bookService;
    }

    @ShellMethod(value = "Create booking", key = "book")
    public String createBooking(String movieName, String roomName, String screeningDate, String seats) {
        String accountName = bookService.getAccount();
        String[] places = seats.split(" ");
        List<Seats> seatList = createSeats(places);
        LocalDateTime screeningLocalDateTime = fomatDate(screeningDate);
        List<Book> bookings = createBookings(accountName, movieName, roomName, screeningLocalDateTime, seatList);


        try {
            bookService.saveBooking(bookings);
        } catch (BookException | CrudException e) {
            return e.getMessage();
        }
        return null;
    }

    private LocalDateTime fomatDate(String screeningDate) {
        return LocalDateTime.parse(screeningDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    private List<Seats> createSeats(String[] seatList) {
        List<Seats> seats = new ArrayList<>();
        for (String rowCol : seatList) {
            String[] places = rowCol.split(",");
            int row = Integer.parseInt(places[0]);
            int col = Integer.parseInt(places[1]);
            seats.add(new Seats(row, col));
        }
        return seats;
    }

    private List<Book> createBookings(String accountName, String movieName,
                                      String roomName, LocalDateTime screeningDate, List<Seats> seatList) {
        List<Book> bookingList = new ArrayList<>();
        for (Seats seats : seatList) {
            bookingList.add(new Book(accountName, movieName, roomName,
                    screeningDate, seats.getRowNum(), seats.getColNum()));
        }
        return bookingList;
    }
}
