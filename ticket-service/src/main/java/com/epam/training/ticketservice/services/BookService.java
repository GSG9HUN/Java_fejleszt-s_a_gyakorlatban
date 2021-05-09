package com.epam.training.ticketservice.services;

import com.epam.training.ticketservice.exceptions.BookException;
import com.epam.training.ticketservice.exceptions.CrudException;
import com.epam.training.ticketservice.modell.Book;
import com.epam.training.ticketservice.repository.impl.JpaBookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final JpaBookRepository jpaBookRepository;

    public BookService(JpaBookRepository jpaBookRepository) {
        this.jpaBookRepository = jpaBookRepository;
    }

    public void saveBooking(List<Book> book) throws CrudException, BookException {
        jpaBookRepository.save(book);
    }

    public List<Book> findAllBookingsForAccount(String accountName) {
        return jpaBookRepository.getAll(accountName);
    }

    public String getAccount() {
        return jpaBookRepository.getAccount();
    }
}
