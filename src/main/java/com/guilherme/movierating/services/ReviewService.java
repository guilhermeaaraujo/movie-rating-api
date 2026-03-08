package com.guilherme.movierating.services;

import com.guilherme.movierating.model.entities.Movie;
import com.guilherme.movierating.model.entities.Review;
import com.guilherme.movierating.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private MovieService movieService;

    public Review insert(Review review) {

        userService.findById(review.getUserId());
        movieService.findById(review.getMovieId());

        return reviewRepository.save(review);
    }

    public void delete(String reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    public List<Review> findByUserId(String userId) {
        return reviewRepository.findByUserId(userId);
    }

    public List<Review> findByMovieId(String movieId) {
        return reviewRepository.findByMovieId(movieId);
    }
}
