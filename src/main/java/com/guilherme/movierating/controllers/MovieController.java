package com.guilherme.movierating.controllers;

import com.guilherme.movierating.config.SecurityConfig;
import com.guilherme.movierating.model.entities.Movie;
import com.guilherme.movierating.model.entities.Review;
import com.guilherme.movierating.services.MovieService;
import com.guilherme.movierating.services.ReviewService;
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
@RequestMapping("/movies")
@Tag(name = "movies", description = "Controlador para operações relacionadas ao CRUD de Filmes")
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private ReviewService reviewService;

    @GetMapping
    @Operation(summary = "Listar filmes", description = "Lista todos os filmes do banco de dados")
    @ApiResponse(responseCode = "200", description = "Filmes retornados com sucesso")
    @ApiResponse(responseCode = "403", description = "Você precisa estar logado para acessar o endpoint")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<List<Movie>> findAll() {
        List<Movie> movies = movieService.findAll();
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar filme", description = "Retorna um filme por id")
    @ApiResponse(responseCode = "200", description = "Filme retornado com sucesso")
    @ApiResponse(responseCode = "403", description = "Você precisa estar logado para acessar o endpoint")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<Movie> findById(@PathVariable String id) {
        Movie movie = movieService.findById(id);
        return ResponseEntity.ok(movie);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(summary = "Criar filme", description = "Insere um novo filme no banco de dados")
    @ApiResponse(responseCode = "201", description = "Filme criado com sucesso")
    @ApiResponse(responseCode = "403", description = "Apenas ADMINs podem acessar esse endpoint")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<Movie> insert(@RequestBody Movie movie) {
        Movie movieCreated = movieService.insert(movie);
        return ResponseEntity.status(HttpStatus.CREATED).body(movieCreated);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar filme", description = "Atualiza as informações de um filme")
    @ApiResponse(responseCode = "200", description = "Filme atualizado com sucesso")
    @ApiResponse(responseCode = "403", description = "Apenas ADMINs podem acessar esse endpoint")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<Movie> update(@PathVariable String id, @RequestBody Movie newMovie) {
        Movie updatedMovie = movieService.update(id, newMovie);
        return ResponseEntity.ok(updatedMovie);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar filme", description = "Deleta um filme do banco de dados")
    @ApiResponse(responseCode = "200", description = "Filme deletado com sucesso")
    @ApiResponse(responseCode = "403", description = "Apenas ADMINs podem acessar esse endpoint")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        movieService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/reviews")
    @Operation(summary = "Críticas de um filme", description = "Retorna todas as reviews de um filme")
    @ApiResponse(responseCode = "200", description = "Reviews retornadas com sucesso")
    @ApiResponse(responseCode = "403", description = "Você precisa estar logado para acessar o endpoint")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<List<Review>> findReviewsByMovieId(@PathVariable String id) {
        List<Review> reviews = reviewService.findByMovieId(id);
        return ResponseEntity.ok(reviews);
    }
}
