package com.epam.training.ticketservice.repository.impl;

import com.epam.training.ticketservice.modell.Movie;
import com.epam.training.ticketservice.dataaccess.dao.MovieDao;
import com.epam.training.ticketservice.dataaccess.projection.MovieProjection;
import com.epam.training.ticketservice.exceptions.CrudException;
import com.epam.training.ticketservice.repository.Repo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JpaMovieRepository implements Repo {

    private final MovieDao movieDao;

    public JpaMovieRepository(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    @Override
    public List<Movie> getAll() {
        List<MovieProjection> movieProjections = movieDao.findAll();
        return mapMovieProjections(movieProjections);
    }

    @Override
    public void save(Object movie) {
        movieDao.save(mapMovie((Movie) movie));
    }

    @Override
    public void update(String name, String movieCategory, String length) throws CrudException {

        int movieLength = Integer.parseInt(length);
        Optional<MovieProjection> movie = findMovieByName(name);
        if (movie.isPresent()) {
            movie.get().setCategory(movieCategory);
            movie.get().setLength(movieLength);
            movieDao.save(movie.get());
            return;
        }
        throw new CrudException("Movie doesn't exist");
    }

    @Override
    public void delete(String name) throws CrudException {
        Optional<MovieProjection> movie = findMovieByName(name);
        if (movie.isPresent()) {
            movieDao.delete(movie.get());
            return;
        }
        throw new CrudException("Movie doesn't exist");
    }

    private MovieProjection mapMovie(Movie movie) {
        return new MovieProjection(movie.getName(), movie.getCategory(), movie.getLength());
    }

    private Movie mapMovie(MovieProjection movieProjection) {
        return new Movie(movieProjection.getName(), movieProjection.getCategory(), movieProjection.getLength());
    }

    private List<Movie> mapMovieProjections(List<MovieProjection> movieProjections) {
        return movieProjections.stream()
                .map(this::mapMovie)
                .collect(Collectors.toList());
    }

    private Optional<MovieProjection> findMovieByName(String movieName) {
        List<MovieProjection> movieProjections = movieDao.findAll();
        return movieProjections.stream()
                .filter(movieProjection -> movieProjection.getName().equals(movieName))
                .findFirst();
    }

}
