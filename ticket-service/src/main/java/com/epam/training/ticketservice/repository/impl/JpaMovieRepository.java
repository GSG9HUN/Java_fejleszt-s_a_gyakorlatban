package com.epam.training.ticketservice.repository.impl;

import com.epam.training.ticketservice.Modell.Movie;
import com.epam.training.ticketservice.dataaccess.dao.MovieDao;
import com.epam.training.ticketservice.dataaccess.projection.MovieProjection;
import com.epam.training.ticketservice.repository.MovieRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
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
        List<Movie> movies = getAll();
        for (Movie movie1:
             movies) {
            System.out.println(movie1);
        }
    }

    private MovieProjection mapMovie(Movie movie){
        return  new MovieProjection(movie.getName(),movie.getCategory(),movie.getLength());
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
