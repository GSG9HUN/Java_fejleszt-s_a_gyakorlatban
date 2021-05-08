package com.epam.training.ticketservice.repository.impl;

import com.epam.training.ticketservice.dataaccess.dao.RoomDao;
import com.epam.training.ticketservice.dataaccess.projection.RoomProjection;
import com.epam.training.ticketservice.exceptions.CrudException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class JpaRoomRepositoryTest {

    @Test
    public void testUpdateMethodWithNonExistentRoom(){
        //given
        String roomName ="Terem";
        String rowNum = "2";
        String colNum = "2";
        RoomDao roomDao = Mockito.mock(RoomDao.class);
        JpaRoomRepository jpaRoomRepository = new JpaRoomRepository(roomDao);
        String result = null;
        //when
        try {
            jpaRoomRepository.update(roomName,rowNum,colNum);
        } catch (CrudException e) {
            result = e.getMessage();
        }
        //then


        assertEquals("Room doesn't exist",result);
    }


    @Test
    public void testUpdateMethodWithExistentRoom() throws CrudException {
        //given
        String roomName ="terem";
        String rowNum = "2";
        String colNum = "3";
        RoomDao roomDao = Mockito.mock(RoomDao.class);
        JpaRoomRepository jpaRoomRepository = new JpaRoomRepository(roomDao);
        when(roomDao.findAll()).thenReturn(List.of(new RoomProjection("terem",2,2),
                new RoomProjection("terem2",3,3)));
        Optional<RoomProjection> room = roomDao.findAll()
                .stream()
                .filter(roomProjection -> roomProjection.getName().equals("terem"))
                .findFirst();
        //when
        jpaRoomRepository.update(roomName,rowNum,colNum);
        //then
        verify(roomDao,times(1)).save(room.get());


    }

    @Test
    public void testDeleteMethodWithNonExistentRoom() throws CrudException {
        //given
        String roomName ="terem";
        RoomDao roomDao = Mockito.mock(RoomDao.class);
        JpaRoomRepository jpaRoomRepository = new JpaRoomRepository(roomDao);
        String result =null;
        //when
        try{
            jpaRoomRepository.delete(roomName);
        }catch (CrudException e){
            result = e.getMessage();
        }
        //then
        assertEquals("Room doesn't exist",result);
    }

    @Test
    public void testDeleteMethodWithExistentRoom() throws CrudException {
        //given
        String roomName ="terem";
       RoomDao roomDao = Mockito.mock(RoomDao.class);
        JpaRoomRepository jpaRoomRepository = new JpaRoomRepository(roomDao);
        when(roomDao.findAll()).thenReturn(List.of(new RoomProjection("terem",2,2),
                new RoomProjection("terem2",3,3)));
        Optional<RoomProjection> room = roomDao.findAll()
                .stream()
                .filter(roomProjection -> roomProjection.getName().equals("terem"))
                .findFirst();

        //when
        jpaRoomRepository.delete(roomName);

        //then
        verify(roomDao,times(1)).delete(room.get());
    }

}