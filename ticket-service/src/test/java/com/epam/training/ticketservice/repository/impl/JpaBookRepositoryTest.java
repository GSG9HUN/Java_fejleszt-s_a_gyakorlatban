package com.epam.training.ticketservice.repository.impl;

import com.epam.training.ticketservice.dataaccess.dao.AccountDao;
import com.epam.training.ticketservice.dataaccess.dao.BookDao;
import com.epam.training.ticketservice.dataaccess.dao.RoomDao;
import com.epam.training.ticketservice.dataaccess.projection.BookProjection;
import com.epam.training.ticketservice.exceptions.BookException;
import com.epam.training.ticketservice.exceptions.CrudException;
import com.epam.training.ticketservice.modell.Book;
import com.epam.training.ticketservice.modell.Room;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

class JpaBookRepositoryTest {
    private BookDao bookDao = Mockito.mock(BookDao.class);
    private RoomDao roomDao = Mockito.mock(RoomDao.class);
    private AccountDao accountDao = Mockito.mock(AccountDao.class);
    
    JpaBookRepository jpaBookRepository = new JpaBookRepository(bookDao,roomDao,accountDao);
    
    
    @Test
    public void testCheckIfSeatIsTakenMethodWithNonExistentSeats() {
        //given
        List<Book> book = List.of( new Book("user","film","terem", LocalDateTime.MAX,5,5));
        Room room = new Room("terem",4 ,4);
        String result = null;
        //when
        try{
            jpaBookRepository.checkIfSeatIsTaken(java.util.Optional.of(room),book);
        }catch (BookException e){
            result=e.getMessage();
        }
        //then
        assertEquals("Seats (5,5) does not exist in this room",result);
    }

    @Test
    public void testCheckIfSeatIsTakenMethodWithTakenSeats() {
        //given
        List<Book> book = List.of( new Book("user","film","terem", LocalDateTime.MAX,5,5));
        Room room = new Room("terem",5 ,5);
        String result = null;
        when(bookDao.findAll()).thenReturn(List.of(new BookProjection("user","film","terem",LocalDateTime.MAX,5,5)));
        //when
        try{
            jpaBookRepository.checkIfSeatIsTaken(java.util.Optional.of(room),book);
        }catch (BookException e){
            result=e.getMessage();
        }
        //then
        assertEquals("Seats (5,5) is already taken",result);
    }

    @Test
    public void testSaveMethodWithNonExistentRoom(){
        //given
        String result = null;
        //when
        try {
            jpaBookRepository.save(List.of(new Book("user","film","terem",LocalDateTime.MAX,5,5)));
        } catch (BookException | CrudException e) {
            result =e.getMessage();
        }
        //then

        assertEquals("Room doesn't exist",result);

    }




}