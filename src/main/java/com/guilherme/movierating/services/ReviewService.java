package com.guilherme.movierating.services;

import com.guilherme.movierating.exceptions.ResourceNotFoundException;
import com.guilherme.movierating.model.entities.Review;
import com.guilherme.movierating.model.entities.User;
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

        // Testa se o filme existe no banco de dados
        movieService.findById(review.getMovieId());

        // Usuários não podem adicionar reviews para outros usuários
        User user = userService.findAuthenticatedUserDetails();
        if(!user.getId().equals(review.getUserId())) {
            throw new RuntimeException("You cannot create an review for another user");
        }

        return reviewRepository.save(review);
    }

    public void delete(String reviewId) {
        User authenticadedUser = userService.findAuthenticatedUserDetails();

        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new ResourceNotFoundException("Review not found")
        );

        // Usuários podem apenas deletar suas próprias reviews
        if(!authenticadedUser.getId().equals(review.getUserId())) {
            throw new RuntimeException("You cannot delete another user's review");
        }

        reviewRepository.deleteById(reviewId);
    }

    public List<Review> findByUserId(String userId) {
        return reviewRepository.findByUserId(userId);
    }

    public List<Review> findByMovieId(String movieId) {
        return reviewRepository.findByMovieId(movieId);
    }
}
