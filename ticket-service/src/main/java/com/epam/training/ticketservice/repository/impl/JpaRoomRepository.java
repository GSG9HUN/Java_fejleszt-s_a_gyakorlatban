package com.epam.training.ticketservice.repository.impl;

import com.epam.training.ticketservice.modell.Room;
import com.epam.training.ticketservice.dataaccess.dao.RoomDao;
import com.epam.training.ticketservice.dataaccess.projection.RoomProjection;
import com.epam.training.ticketservice.exceptions.CrudException;
import com.epam.training.ticketservice.repository.Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JpaRoomRepository implements Repo {

    private final RoomDao roomDao;

    @Autowired
    public JpaRoomRepository(RoomDao roomDao) {
        this.roomDao = roomDao;
    }

    @Override
    public List<Room> getAll() {
        return roomDao.findAll().stream()
                .map(this::mapRoom).collect(Collectors.toList());
    }

    @Override
    public void save(Object o) {
        RoomProjection roomProjection = mapRoom((Room) o);
        roomDao.save(roomProjection);
    }

    @Override
    public void update(String name, String param2, String param3) throws CrudException {
        int rowNum = Integer.parseInt(param2);
        int colNum = Integer.parseInt(param3);
        Optional<RoomProjection> room = findRoomByName(name);
        if (room.isPresent()) {
            room.get().setRowNum(rowNum);
            room.get().setColNum(colNum);
            roomDao.save(room.get());
            return;
        }
        throw new CrudException("Room doesn't exist");

    }

    @Override
    public void delete(String name) throws CrudException {
        Optional<RoomProjection> room = findRoomByName(name);
        if (room.isPresent()) {
            roomDao.delete(room.get());
            return;
        }
        throw new CrudException("Room doesn't exist");
    }


    private RoomProjection mapRoom(Room room) {
        return new RoomProjection(room.getName(), room.getRowNum(), room.getColNum());
    }

    private Room mapRoom(RoomProjection roomProjection) {
        return new Room(roomProjection.getName(), roomProjection.getRowNum(), roomProjection.getColNum());
    }


    private Optional<RoomProjection> findRoomByName(String roomName) {
        List<RoomProjection> roomProjections = roomDao.findAll();
        return roomProjections.stream()
                .filter(roomProjection -> roomProjection.getName().equals(roomName))
                .findFirst();
    }

}
