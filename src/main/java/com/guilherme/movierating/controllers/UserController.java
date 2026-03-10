package com.guilherme.movierating.controllers;

import com.guilherme.movierating.config.SecurityConfig;
import com.guilherme.movierating.model.entities.Review;
import com.guilherme.movierating.services.ReviewService;
import com.guilherme.movierating.services.UserService;
import com.guilherme.movierating.model.dto.response.UserDTO;
import com.guilherme.movierating.model.dto.request.UserUpdateRequest;
import com.guilherme.movierating.model.entities.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "users", description = "Controlador para operações relacionadas ao CRUD de Usuários")
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ReviewService reviewService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    @Operation(summary = "Listar usuários", description = "Lista todos os usuários do banco de dados")
    @ApiResponse(responseCode = "200", description = "Usuários retornados com sucesso")
    @ApiResponse(responseCode = "403", description = "Apenas ADMINs podem acessar esse endpoint")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<List<UserDTO>> findAll() {
        List<User> users = userService.findAll();
        List<UserDTO> usersDTO = users.stream().map(UserDTO::new).toList();
        return ResponseEntity.ok(usersDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
        @Operation(summary = "Buscar usuário", description = "Retorna um usuário por id")
    @ApiResponse(responseCode = "200", description = "Usuário retornado com sucesso")
    @ApiResponse(responseCode = "403", description = "Apenas ADMINs podem acessar esse endpoint")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<UserDTO> findById(@PathVariable String id) {
        User user = userService.findById(id);
        UserDTO userDTO = new UserDTO(user);
        return ResponseEntity.ok(userDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(summary = "Criar usuário", description = "Insere um novo usuário no banco de dados")
    @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso")
    @ApiResponse(responseCode = "403", description = "Apenas ADMINs podem acessar esse endpoint")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<UserDTO> insert(@RequestBody User user) {
        User userCreated = userService.insert(user);
        UserDTO userDTO = new UserDTO(userCreated);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário", description = "Atualiza as informações de um usuário")
    @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso")
    @ApiResponse(responseCode = "403", description = "Apenas ADMINs podem acessar esse endpoint")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<UserDTO> update(@PathVariable String id, @RequestBody UserUpdateRequest request) {
        User updatedUser = userService.update(id, request);
        UserDTO userDTO = new UserDTO(updatedUser);
        return ResponseEntity.ok(userDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar usuário", description = "Deleta um usuário do banco de dados")
    @ApiResponse(responseCode = "200", description = "Usuário deletado com sucesso")
    @ApiResponse(responseCode = "403", description = "Apenas ADMINs podem acessar esse endpoint")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    @Operation(summary = "Meus dados", description = "Busca os dados do usuário autenticado")
    @ApiResponse(responseCode = "200", description = "Usuário retornado com sucesso")
    @ApiResponse(responseCode = "403", description = "Você precisa estar logado para acessar o endpoint")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<UserDTO> findAuthenticatedUserDetails() {
        User authenticatedUser = userService.findAuthenticatedUserDetails();

        return ResponseEntity.ok(new UserDTO(authenticatedUser));
    }

    @GetMapping("/{id}/reviews")
    @Operation(summary = "Críticas de um usuário", description = "Retorna todas as reviews de um usuário")
    @ApiResponse(responseCode = "200", description = "Reviews retornadas com sucesso")
    @ApiResponse(responseCode = "403", description = "Você precisa estar logado para acessar o endpoint")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<List<Review>> findReviewsByUserId(@PathVariable String id) {
        List<Review> reviews = reviewService.findByUserId(id);
        return ResponseEntity.ok(reviews);
    }
}
