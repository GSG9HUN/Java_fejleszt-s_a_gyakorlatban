package com.epam.training.ticketservice.controllers;

import com.epam.training.ticketservice.modell.AdminAccount;
import com.epam.training.ticketservice.modell.Room;
import com.epam.training.ticketservice.exceptions.EmptyListException;
import com.epam.training.ticketservice.exceptions.CrudException;
import com.epam.training.ticketservice.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@ShellComponent
public class RoomController {

    RoomService roomService;

    @Autowired
    RoomController(RoomService roomService) {
        this.roomService = roomService;
    }


    @ShellMethod(value = "Create room", key = "create room")
    public String createRoom(String roomName, int rowNum, int colNum) {

        if (AdminAccount.isLogedIn()) {
            roomService.createRoom(roomName, rowNum, colNum);
            return "Room created";
        }
        return "You are not signed in";

    }


    @ShellMethod(value = "List all rooms", key = "list rooms")
    public String list() {
        List<Room> rooms;
        try {
            rooms = roomService.listRooms();
        } catch (EmptyListException e) {
            return e.getMessage();
        }

        for (Room room : rooms) {
            int seats = room.getColNum() * room.getRowNum();
            System.out.println("Room " + room.getName() + " with " + seats + " seats, "
                    + room.getRowNum() + " rows and " + room.getColNum() + " columns");
        }
        return null;
    }


    @ShellMethod(value = "Update existing room", key = "update room")
    public String updateRoom(String roomName, String rowNum, String colNum) {

        if (AdminAccount.isLogedIn()) {
            try {
                roomService.updateRoom(roomName, rowNum, colNum);
            } catch (CrudException e) {
                return e.getMessage();
            }

            return "Room updated";
        }

        return "You are not signed in";

    }


    @ShellMethod(value = "Delete existing room", key = "delete room")
    public String deleteRoom(String roomName) {

        if (AdminAccount.isLogedIn()) {
            try {
                roomService.deleteRoom(roomName);
            } catch (CrudException e) {
                return e.getMessage();
            }

            return "Room deleted";
        }

        return "You are not signed in";

    }
}
