package com.guilherme.movierating.controllers;

import com.guilherme.movierating.model.entities.Review;
import com.guilherme.movierating.services.ReviewService;
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
    public ResponseEntity<Review> insert(@RequestBody Review review) {
        Review reviewCreated = reviewService.insert(review);
        return ResponseEntity.status(HttpStatus.CREATED).body(review);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        reviewService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
