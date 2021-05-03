package com.epam.training.ticketservice.repository;
import com.epam.training.ticketservice.Modell.Room;
import com.epam.training.ticketservice.exceptions.CrudException;

import java.util.List;

public interface RoomRepository {
    List<Room> getAll();
    void saveRoom(Room room);
    Room updateRoom(String movieName, int movieCategory, int movieLength) throws CrudException;
    void deleteRoom(String movieName) throws CrudException;

}
