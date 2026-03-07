package com.guilherme.movierating.controllers;

import com.guilherme.movierating.model.entities.Movie;
import com.guilherme.movierating.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;
    
    @GetMapping
    public ResponseEntity<List<Movie>> findAll() {
        List<Movie> movies = movieService.findAll();
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> findById(@PathVariable String id) {
        Movie movie = movieService.findById(id);
        return ResponseEntity.ok(movie);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Movie> insert(@RequestBody Movie movie) {
        Movie movieCreated = movieService.insert(movie);
        return ResponseEntity.status(HttpStatus.CREATED).body(movieCreated);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Movie> update(@PathVariable String id, @RequestBody Movie newMovie) {
        Movie updatedMovie = movieService.update(id, newMovie);
        return ResponseEntity.ok(updatedMovie);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        movieService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
