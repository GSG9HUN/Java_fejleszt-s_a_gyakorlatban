package com.epam.training.ticketservice.controllers;
import com.epam.training.ticketservice.controllers.SignInOutController;
import com.epam.training.ticketservice.exceptions.CrudException;
import com.epam.training.ticketservice.exceptions.EmptyListException;
import com.epam.training.ticketservice.modell.AdminAccount;
import com.epam.training.ticketservice.modell.Room;
import com.epam.training.ticketservice.services.RoomService;
import com.epam.training.ticketservice.services.SignInOutService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoomControllerTest {
    RoomService roomService = Mockito.mock(RoomService.class);
    RoomController roomController = new RoomController(roomService);

    @Test
    public void testCreateRoomMethodWithoutLogIn(){
        //given
        String result;
        AdminAccount.setIsLogedIn(false);
        //when
        result=roomController.createRoom("terem",2,2);
        //then
        assertEquals("You are not signed in",result);
    }

    @Test
    public void testCreateRoomMethodWithLogIn(){
        //given
        String result;
        AdminAccount.setIsLogedIn(true);
        //when
        result=roomController.createRoom("terem",2,2);
        //then
        assertEquals("Room created",result);
    }

    @Test
    public void testListRoomMethodWithNoRooms() throws EmptyListException {
        //given
        String result;
        when(roomService.listRooms()).thenThrow(new EmptyListException("Theres no rooms at the moment"));
        //when
        result=roomController.list();
        //then
        assertEquals("Theres no rooms at the moment",result);
    }

    @Test
    public void testListRoomMethod() throws EmptyListException {
        //given
        String result=null;
        when(roomService.listRooms()).thenReturn(List.of(new Room("terem",2,2)));
        //when
        result=roomController.list();
        //then
        assertNull(result);
    }

    @Test
    public void testUpdateRoomMethodWithOutLogIn() {
        //given
        String result=null;
        AdminAccount.setIsLogedIn(false);
        //when
        result=roomController.updateRoom("terem","2","2");
        //then
        assertEquals("You are not signed in",result);
    }
    @Test
    public void testUpdateRoomMethodWithNonExistentRoom() throws CrudException {
        //given
        String result=null;
        AdminAccount.setIsLogedIn(true);
        doThrow(new CrudException("Room doesn't exist")).when(roomService).updateRoom("terem","2","2");
        //when
        result=roomController.updateRoom("terem","2","2");
        //then
        assertEquals("Room doesn't exist",result);
    }

    @Test
    public void testUpdateRoomMethodWithExistentRoom() throws CrudException {
        //given
        String result=null;
        AdminAccount.setIsLogedIn(true);
        doNothing().when(roomService).updateRoom("Terem","2","2");
        //when
        result=roomController.updateRoom("Terem","2","2");
        //then
        assertEquals("Room updated",result);
    }


    @Test
    public void testDeleteRoomMethodWithOutLogIn(){
        //given
        String result;
        AdminAccount.setIsLogedIn(false);
        //when
        result =roomController.deleteRoom("Terem");
        //then
        assertEquals("You are not signed in",result);
    }
    @Test
    public void testDeleteRoomMethodWithNonExistentRoom() throws CrudException {
        //given
        String result;
        AdminAccount.setIsLogedIn(true);
        doThrow(new CrudException("Room doesn't exist")).when(roomService).deleteRoom("Terem");
        //when
        result =roomController.deleteRoom("Terem");
        //then
        assertEquals("Room doesn't exist",result);
    }
    @Test
    public void testDeleteRoomMethod() throws CrudException {
        //given
        String result;
        AdminAccount.setIsLogedIn(true);
        doNothing().when(roomService).deleteRoom("Terem");
        //when
        result =roomController.deleteRoom("Terem");
        //then
        assertEquals("Room deleted",result);
    }



}