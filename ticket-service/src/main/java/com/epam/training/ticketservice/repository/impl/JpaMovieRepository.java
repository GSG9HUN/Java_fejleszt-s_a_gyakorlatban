package com.epam.training.ticketservice.repository.impl;

import com.epam.training.ticketservice.Modell.Movie;
import com.epam.training.ticketservice.dataaccess.dao.MovieDao;
import com.epam.training.ticketservice.dataaccess.projection.MovieProjection;
import com.epam.training.ticketservice.exceptions.CrudException;
import com.epam.training.ticketservice.repository.MovieRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JpaMovieRepository implements MovieRepository {

    private final MovieDao movieDao;

    public JpaMovieRepository(MovieDao movieDao){
        this.movieDao=movieDao;
    }

    @Override
    public List<Movie> getAll() {
        List<MovieProjection> movieProjections = movieDao.findAll();
        return mapMovieProjections(movieProjections);
    }

    @Override
    public void saveMovie(Movie movie) {
        movieDao.save(mapMovie(movie));
    }

    @Override
    public Movie updateMovie(String movieName, String movieCategory, int movieLength) throws CrudException {

        Optional<MovieProjection> movie = findMovieByName(movieName);
        if(movie.isPresent()){
            movie.get().setCategory(movieCategory);
            movie.get().setLength(movieLength);
            movieDao.save(movie.get());
            throw new CrudException("Movie updated");
        }
        throw new CrudException("Movie doesn't exist");

    }

    @Override
    public void deleteMovie(String movieName) throws CrudException {
        Optional<MovieProjection> movie = findMovieByName(movieName);
        if(movie.isPresent()){
            movieDao.delete(movie.get());
            throw new CrudException("Movie deleted");
        }
        throw new CrudException("Movie doesn't exist");
    }

    private MovieProjection mapMovie(Movie movie){
        return  new MovieProjection(movie.getName(),movie.getCategory(),movie.getLength());
    }


    private Optional<MovieProjection> findMovieByName(String movieName){
        List<MovieProjection> movieProjections = movieDao.findAll();
        return movieProjections.stream()
                .filter( movieProjection -> movieProjection.getName().equals(movieName))
                .findFirst();
    }

    private List<Movie> mapMovieProjections(List<MovieProjection> movieProjections){
    return movieProjections.stream()
            .map(this::mapMovie)
            .collect(Collectors.toList());
    }

    private Movie mapMovie(MovieProjection movieProjection){
        return new Movie(movieProjection.getName(),movieProjection.getCategory(),movieProjection.getLength());
    }
}
