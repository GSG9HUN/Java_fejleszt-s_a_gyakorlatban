package com.epam.training.ticketservice.repository.impl;

import com.epam.training.ticketservice.Modell.Room;
import com.epam.training.ticketservice.dataaccess.dao.RoomDao;
import com.epam.training.ticketservice.dataaccess.projection.MovieProjection;
import com.epam.training.ticketservice.dataaccess.projection.RoomProjection;
import com.epam.training.ticketservice.exceptions.CrudException;
import com.epam.training.ticketservice.repository.RoomRepository;
import jdk.jfr.Registered;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JpaRoomRepository implements RoomRepository {

    private final RoomDao roomDao;

    @Autowired
    public JpaRoomRepository(RoomDao roomDao){
        this.roomDao=roomDao;
    }
    @Override
    public List<Room> getAll() {
        return roomDao.findAll().stream()
                .map(this::mapRoom).collect(Collectors.toList());
    }

    @Override
    public void saveRoom(Room room) {

        RoomProjection roomProjection = mapRoom(room);
        roomDao.save(roomProjection);
    }

    @Override
    public Room updateRoom(String roomName, int rowNum, int colNum) throws CrudException {

        Optional<RoomProjection> room = findRoomByName(roomName);
        if(room.isPresent()){
            room.get().setRowNum(rowNum);
            room.get().setColNum(colNum);
            roomDao.save(room.get());
            throw new CrudException("Room updated");
        }
        throw new CrudException("Room doesn't exist");

    }

    @Override
    public void deleteRoom(String movieName) throws CrudException {
        Optional<RoomProjection> movie = findRoomByName(movieName);
        if(movie.isPresent()){
            roomDao.delete(movie.get());
            throw new CrudException("Room deleted");
        }
        throw new CrudException("Room doesn't exist");
    }

    private RoomProjection mapRoom(Room room){
        return  new RoomProjection(room.getName(),room.getRowNum(),room.getColNum());
    }


    private Optional<RoomProjection> findRoomByName(String roomName){
        List<RoomProjection> roomProjections = roomDao.findAll();
        return roomProjections.stream()
                .filter( movieProjection -> movieProjection.getName().equals(roomName))
                .findFirst();
    }

    private List<Room> mapRoomProjection(List<RoomProjection> roomProjections){
        return roomProjections.stream()
                .map(this::mapRoom)
                .collect(Collectors.toList());
    }

    private Room mapRoom(RoomProjection roomProjection){
        return new Room(roomProjection.getName(),roomProjection.getRowNum(),roomProjection.getColNum());
    }
}
