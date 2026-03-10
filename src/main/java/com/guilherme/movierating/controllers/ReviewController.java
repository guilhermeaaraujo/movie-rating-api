package com.guilherme.movierating.controllers;

import com.guilherme.movierating.model.entities.Review;
import com.guilherme.movierating.services.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    @Operation(summary = "Criar crítica", description = "Insere uma nova review no banco de dados")
    @ApiResponse(responseCode = "201", description = "Review criada com sucesso")
    @ApiResponse(responseCode = "403", description = "Você precisa estar logado para acessar o endpoint")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<Review> insert(@RequestBody Review review) {
        Review reviewCreated = reviewService.insert(review);
        return ResponseEntity.status(HttpStatus.CREATED).body(review);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar crítica", description = "Deleta uma review do banco de dados")
    @ApiResponse(responseCode = "200", description = "Review deletada com sucesso")
    @ApiResponse(responseCode = "403", description = "Você precisa estar logado para acessar o endpoint")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        reviewService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
