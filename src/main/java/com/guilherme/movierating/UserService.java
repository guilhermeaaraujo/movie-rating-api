package com.guilherme.movierating;

import com.guilherme.movierating.model.entities.User;
import com.guilherme.movierating.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(String id) {
        return repository.findById(id).orElseThrow(
                () -> new RuntimeException("Resource not Found")
        );
    }

    public User insert(User user) {
        return repository.insert(user);
    }

    public User update(User newUser) {
        User user = findById(newUser.id);
        updateData(user, newUser);
        return repository.save(user);
    }

    public void delete(String id) {
        findById(id);
        repository.deleteById(id);
    }

    // Método Auxiliar
    private void updateData(User user, User newUser) {
        user.setName(newUser.getName());
        user.setEmail(newUser.getEmail());
    }
}
