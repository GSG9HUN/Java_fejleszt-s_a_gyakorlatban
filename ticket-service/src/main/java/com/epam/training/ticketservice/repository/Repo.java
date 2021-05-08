package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.exceptions.CrudException;

import java.util.List;

public interface Repo<T> {
    List<T> getAll();

    void save(T t);

    void update(String name, String param2, String param3) throws CrudException;

    void delete(String name) throws CrudException;

}
