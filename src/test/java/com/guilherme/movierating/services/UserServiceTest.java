package com.guilherme.movierating.services;

import com.guilherme.movierating.model.entities.User;
import com.guilherme.movierating.repositories.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService service;

    @Test
    @DisplayName("Should return an list of users")
    void findAll() {
        when(repository.findAll()).thenReturn(List.of(new User(), new User()));

        List<User> result = service.findAll();

        assertThat(result).hasSize(2);
        verify(repository).findAll();
    }

    @Test
    @DisplayName("Should successfully return an user by id")
    void findById() {
        String id = "testid";
        User user = new User();
        user.setId(id);
        when(repository.findById(id)).thenReturn(Optional.of(user));

        User result = service.findById(id);

        assertThat(result.getId()).isEqualTo(id);
        verify(repository).findById(id);
    }

    @Test
    @DisplayName("Should throw a RuntimeException when an user does not exists")
    void findByIdShouldThrowException() {
        String id = "testid";

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(id)).isInstanceOf(RuntimeException.class);
        verify(repository).findById(id);
    }

    @Test
    @DisplayName("Should successfully create a new user")
    void insert() {
        User user = new User();
        user.setPassword("123");
        when(repository.save(user)).thenReturn(user);
        when(passwordEncoder.encode("123")).thenReturn("test");

        User result = service.insert(user);

        assertThat(result).isEqualTo(user);
        verify(repository).save(user);
    }

    @Test
    @DisplayName("Should throw a RuntimeException when an user already exists")
    void insertShouldThrowException() {
        String email = "test@email.com";

        User user = new User();
        user.setEmail(email);

        when(repository.findByEmail(email)).thenReturn(Optional.of(user));

        assertThatThrownBy(() -> service.insert(user)).isInstanceOf(RuntimeException.class);
        verify(repository).findByEmail(email);
    }
}