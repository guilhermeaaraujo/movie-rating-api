package com.guilherme.movierating.controllers;

import com.guilherme.movierating.config.TokenService;
import com.guilherme.movierating.model.dto.request.LoginRequest;
import com.guilherme.movierating.model.dto.request.RegisterRequest;
import com.guilherme.movierating.model.dto.response.LoginResponse;
import com.guilherme.movierating.model.dto.response.UserDTO;
import com.guilherme.movierating.model.entities.User;
import com.guilherme.movierating.model.enums.UserRole;
import com.guilherme.movierating.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        UsernamePasswordAuthenticationToken usernamepassword = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        var authentication = authenticationManager.authenticate(usernamepassword);

        User user = (User) authentication.getPrincipal();
        String token = tokenService.generateToken(user);
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody RegisterRequest request) {
        if (repository.findByEmail(request.email()).isPresent()) return ResponseEntity.badRequest().build();

        String encodedPassword = passwordEncoder.encode(request.password());
        User user = new User(request.name(), request.email(), encodedPassword, UserRole.USER);
        repository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(new UserDTO(user));
    }
}
