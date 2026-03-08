package com.guilherme.movierating.services;

import com.guilherme.movierating.model.entities.Movie;
import com.guilherme.movierating.repositories.MovieRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    private MovieRepository repository;

    @InjectMocks
    private MovieService service;

    @Test
    @DisplayName("Should return an list of movies")
    void findAll() {
        when(repository.findAll()).thenReturn(List.of(new Movie(), new Movie()));

        List<Movie> result = service.findAll();

        assertThat(result).hasSize(2);
        verify(repository).findAll();
    }

    @Test
    @DisplayName("Should successfully return an movie by id")
    void findById() {
        String id = "testid";
        Movie movie = new Movie();
        movie.setId(id);
        when(repository.findById(id)).thenReturn(Optional.of(movie));

        Movie result = service.findById(id);

        assertThat(result.getId()).isEqualTo(id);
        verify(repository).findById(id);
    }

    @Test
    @DisplayName("Should throw a RuntimeException when an movie does not exists")
    void findByIdShouldThrowException() {
        String id = "testid";

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(id)).isInstanceOf(RuntimeException.class);
        verify(repository).findById(id);
    }

    @Test
    @DisplayName("Should successfully create a new movie")
    void insert() {
        Movie movie = new Movie();
        when(repository.save(movie)).thenReturn(movie);

        Movie result = service.insert(movie);

        assertThat(result).isEqualTo(movie);
        verify(repository).save(movie);
    }
}