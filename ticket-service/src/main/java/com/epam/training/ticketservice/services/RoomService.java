package com.epam.training.ticketservice.services;

import com.epam.training.ticketservice.Modell.Room;
import com.epam.training.ticketservice.exceptions.EmptyListException;
import com.epam.training.ticketservice.exceptions.CrudException;
import com.epam.training.ticketservice.repository.impl.JpaRoomRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoomService {
    private JpaRoomRepository jpaRoomRepository;

    public RoomService(JpaRoomRepository jpaRoomRepository) {
        this.jpaRoomRepository=jpaRoomRepository;
    }

    public void createRoom(String roomName, int rowNum, int colNum){

        Room room = new Room(roomName,rowNum,colNum);
       jpaRoomRepository.saveRoom(room);

    }

    public List<Room> listRooms() throws EmptyListException {
        List<Room> rooms = jpaRoomRepository.getAll();
        if(rooms.isEmpty()){
            throw new EmptyListException("Theres no rooms at the moment");
        }
        return  rooms;
    }


    public void deleteRoom(String roomName) throws CrudException {
        jpaRoomRepository.deleteRoom(roomName);
    }

    public void updateRoom(String roomName, int rowNum, int colNum) throws CrudException {
        jpaRoomRepository.updateRoom(roomName,rowNum,colNum);
    }
}
