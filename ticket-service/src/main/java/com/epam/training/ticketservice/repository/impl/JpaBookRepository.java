package com.epam.training.ticketservice.repository.impl;

import com.epam.training.ticketservice.dataaccess.dao.AccountDao;
import com.epam.training.ticketservice.dataaccess.dao.BookDao;
import com.epam.training.ticketservice.dataaccess.dao.RoomDao;
import com.epam.training.ticketservice.dataaccess.projection.AccountProjection;
import com.epam.training.ticketservice.dataaccess.projection.BookProjection;
import com.epam.training.ticketservice.dataaccess.projection.RoomProjection;
import com.epam.training.ticketservice.exceptions.BookException;
import com.epam.training.ticketservice.exceptions.CrudException;
import com.epam.training.ticketservice.modell.Account;
import com.epam.training.ticketservice.modell.Book;
import com.epam.training.ticketservice.modell.Room;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JpaBookRepository {

    private final BookDao bookDao;
    private final RoomDao roomDao;
    private final AccountDao accountDao;

    public JpaBookRepository(BookDao bookDao, RoomDao roomDao, AccountDao accountDao) {
        this.bookDao = bookDao;
        this.roomDao = roomDao;
        this.accountDao = accountDao;
    }

    public void save(List<Book> bookings) throws BookException, CrudException {
        Optional<Room> room = findRoom(bookings.get(0).getRoomName());

        if (room.isEmpty()) {
            throw new CrudException("Room doesn't exist");
        }

        checkIfSeatIsTaken(room, bookings);

        saveBookings(bookings);
    }


    public void checkIfSeatIsTaken(Optional<Room> room, List<Book> bookings) throws BookException {
        for (Book booking : bookings) {
            if (!roomSeatsExist(room.get(), booking)) {
                throw new BookException("Seats (" + booking.getRowNum() + ","
                        + booking.getColNum() + ") does not exist in this room");
            }

            if (seatIsTaken(booking)) {
                throw new BookException("Seats (" + booking.getRowNum() + ","
                        + booking.getColNum() + ") is already taken");
            }
        }
    }

    private void saveBookings(List<Book> bookings) {
        for (Book booking : bookings) {
            bookDao.save(mapBook(booking));
        }
    }


    private boolean roomSeatsExist(Room room, Book book) {
        return book.getColNum() <= room.getColNum()
                && book.getRowNum() <= room.getRowNum();
    }

    public List<Book> getAll(String accountName) {
        return bookDao.findAll()
                .stream()
                .filter(bookProjection -> bookProjection.getAccountName().equals(accountName))
                .map(this::mapBook)
                .collect(Collectors.toList());
    }

    private boolean seatIsTaken(Book book) {
        Optional<Book> takenSeats = bookDao.findAll()
                .stream()
                .filter(bookProjection -> bookProjection.getMovieName().equals(book.getMovieName())
                        && bookProjection.getRoomName().equals(book.getRoomName())
                        && bookProjection.getScreeningDate().equals(book.getScreeningDate())
                        && bookProjection.getColNum() == book.getColNum()
                        && bookProjection.getRowNum() == book.getRowNum())
                .map(this::mapBook)
                .findFirst();
        return takenSeats.isPresent();

    }

    private Optional<Room> findRoom(String roomName) {
        return roomDao.findAll()
                .stream()
                .filter(roomProjection -> roomProjection.getName().equals(roomName))
                .map(this::mapRoom)
                .findFirst();

    }

    private Room mapRoom(RoomProjection roomProjection) {
        return new Room(roomProjection.getName(), roomProjection.getRowNum(), roomProjection.getColNum());
    }


    private BookProjection mapBook(Book book) {
        return new BookProjection(book.getAccountName(), book.getMovieName(),
                book.getRoomName(), book.getScreeningDate(), book.getRowNum(), book.getColNum());
    }

    private Book mapBook(BookProjection book) {
        return new Book(book.getAccountName(), book.getMovieName(),
                book.getRoomName(), book.getScreeningDate(), book.getRowNum(), book.getColNum());
    }

    public String getAccount() {
        return accountDao.findAll()
                .stream()
                .filter(AccountProjection::isLoggedIn)
                .findFirst()
                .map(this::mapAccount)
                .get()
                .getUsername();
    }

    private Account mapAccount(AccountProjection accountProjection) {
        return new Account(accountProjection.getUsername(),
                accountProjection.getPassword(), accountProjection.isLoggedIn());
    }
}
