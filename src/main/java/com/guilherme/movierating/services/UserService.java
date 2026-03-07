package com.guilherme.movierating.services;

import com.guilherme.movierating.model.dto.request.UserUpdateRequest;
import com.guilherme.movierating.model.entities.User;
import com.guilherme.movierating.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(String id) {
        return repository.findById(id).orElseThrow(
                () -> new RuntimeException("Resource not Found")
        );
    }

    public User insert(User user) {
        if (repository.findByEmail(user.getEmail()).isPresent()) throw new RuntimeException("This email is already taken");
        encodePassword(user);
        return repository.save(user);
    }

    public User update(String id, UserUpdateRequest request) {
        User user = findById(id);
        updateData(user, request);
        return repository.save(user);
    }

    public void delete(String id) {
        try {
        repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
        throw new RuntimeException("User not found");
        } catch (DataIntegrityViolationException e) {
        throw new RuntimeException("Cannot delete this user");
        }
    }

    public User findAuthenticatedUserDetails() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (User) auth.getPrincipal();
    }

    // Método Auxiliar
    private void updateData(User user, UserUpdateRequest request) {
        user.setName(request.name());
        user.setEmail(request.email());
    }

    private void encodePassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }
}
