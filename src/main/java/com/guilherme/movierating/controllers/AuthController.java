package com.guilherme.movierating.controllers;

import com.guilherme.movierating.config.SecurityConfig;
import com.guilherme.movierating.config.TokenService;
import com.guilherme.movierating.model.dto.request.LoginRequest;
import com.guilherme.movierating.model.dto.request.RegisterRequest;
import com.guilherme.movierating.model.dto.response.LoginResponse;
import com.guilherme.movierating.model.dto.response.UserDTO;
import com.guilherme.movierating.model.entities.User;
import com.guilherme.movierating.model.enums.UserRole;
import com.guilherme.movierating.repositories.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "authentication", description = "Controlador para autenticação e registro de usuários")
@SecurityRequirement(name = SecurityConfig.SECURITY)
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
    @Operation(summary = "Logar usuário", description = "Metódo de login")
    @ApiResponse(responseCode = "200", description = "Usuário logado com sucesso")
    @ApiResponse(responseCode = "400", description = "Email ou senha incorretos")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        UsernamePasswordAuthenticationToken usernamepassword = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        var authentication = authenticationManager.authenticate(usernamepassword);

        User user = (User) authentication.getPrincipal();
        String token = tokenService.generateToken(user);
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @Operation(summary = "Registrar usuário", description = "Registra um novo usuário no banco de dados")
    @ApiResponse(responseCode = "201", description = "Usuário registrado com sucesso")
    @ApiResponse(responseCode = "403", description = "Email ou senha incorretos")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody RegisterRequest request) {
        if (repository.findByEmail(request.email()).isPresent()) return ResponseEntity.badRequest().build();

        String encodedPassword = passwordEncoder.encode(request.password());
        User user = new User(request.name(), request.email(), encodedPassword, UserRole.USER);
        repository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(new UserDTO(user));
    }
}
