package com.guilherme.movierating.services;

import com.guilherme.movierating.model.entities.Movie;
import com.guilherme.movierating.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    @Autowired
    private MovieRepository repository;

    public List<Movie> findAll() {
        return repository.findAll();
    }

    public Movie findById(String id) {
        return repository.findById(id).orElseThrow(
                () -> new RuntimeException("Movie not Found")
        );
    }

    public Movie insert(Movie movie) {
        return repository.save(movie);
    }

    public Movie update(String id, Movie newMovie) {
        Movie movie = findById(id);
        updateData(movie, newMovie);
        return repository.save(movie);
    }

    public void delete(String id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("Movie not found");
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Cannot delete this movie");
        }
    }

    // Método Auxiliar
    private void updateData(Movie movie, Movie newMovie) {
        movie.setTitle(newMovie.getTitle());
        movie.setSynopsis(newMovie.getSynopsis());
    }
}
