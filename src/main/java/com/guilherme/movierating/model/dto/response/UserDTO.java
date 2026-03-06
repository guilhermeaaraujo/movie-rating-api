package com.guilherme.movierating.model.dto.response;

import com.guilherme.movierating.model.entities.User;

public record UserDTO(String id, String name) {

    public UserDTO(User user) {
        this(user.getId(), user.getName());
    }
}
