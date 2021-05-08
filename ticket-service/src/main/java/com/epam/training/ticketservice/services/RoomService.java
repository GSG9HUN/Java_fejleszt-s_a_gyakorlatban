package com.epam.training.ticketservice.services;

import com.epam.training.ticketservice.modell.Room;
import com.epam.training.ticketservice.exceptions.EmptyListException;
import com.epam.training.ticketservice.exceptions.CrudException;
import com.epam.training.ticketservice.repository.impl.JpaRoomRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoomService {
    private JpaRoomRepository jpaRoomRepository;

    public RoomService(JpaRoomRepository jpaRoomRepository) {
        this.jpaRoomRepository = jpaRoomRepository;
    }

    public void createRoom(String roomName, int rowNum, int colNum) {

        Room room = new Room(roomName, rowNum, colNum);
        jpaRoomRepository.save(room);

    }

    public List<Room> listRooms() throws EmptyListException {
        List<Room> rooms = jpaRoomRepository.getAll();
        if (rooms.isEmpty()) {
            throw new EmptyListException("Theres no rooms at the moment");
        }
        return rooms;
    }


    public void deleteRoom(String roomName) throws CrudException {
        jpaRoomRepository.delete(roomName);
    }

    public void updateRoom(String roomName, String rowNum, String colNum) throws CrudException {
        jpaRoomRepository.update(roomName, rowNum, colNum);
    }
}
