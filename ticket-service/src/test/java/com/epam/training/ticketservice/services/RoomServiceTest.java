package com.epam.training.ticketservice.services;

import com.epam.training.ticketservice.exceptions.EmptyListException;
import com.epam.training.ticketservice.modell.Room;
import com.epam.training.ticketservice.repository.impl.JpaRoomRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;


class RoomServiceTest {
    JpaRoomRepository jpaRoomRepository = Mockito.mock(JpaRoomRepository.class);

    @Test
    public void testListRoomsMethodWithNoRooms(){
        //given
        String result =null;
        RoomService roomService = new RoomService(jpaRoomRepository);
        //when
        try {
            roomService.listRooms();
        } catch (EmptyListException e) {
            result=e.getMessage();
        }
        //then
        assertEquals("Theres no rooms at the moment",result);
    }
    @Test
    public void testListRomsMethod() throws EmptyListException {
        //given;
        RoomService roomService = new RoomService(jpaRoomRepository);
        when(jpaRoomRepository.getAll()).thenReturn(List.of(new Room("terem",2,2)));
        //when

        List<Room> rooms=roomService.listRooms();
        //then
        assertNotNull(rooms);
    }

}