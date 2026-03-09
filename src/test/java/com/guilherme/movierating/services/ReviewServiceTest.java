package com.guilherme.movierating.services;

import com.guilherme.movierating.model.entities.Review;
import com.guilherme.movierating.repositories.ReviewRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewService reviewService;

    @Test
    @DisplayName("Should sucessfully return an list of reviews by its user id")
    void findByUserId() {
        String userId = "user1";

        Review review = new Review();
        review.setUserId(userId);

        when(reviewRepository.findByUserId(userId)).thenReturn(List.of(review));

        List<Review> result = reviewService.findByUserId(userId);

        assertThat(result).hasSize(1);
        verify(reviewRepository).findByUserId(userId);
    }

    @Test
    @DisplayName("Should sucessfully return an list of reviews by its movie id")
    void findByMovieId() {
        String movieId = "movie1";

        Review review = new Review();
        review.setMovieId(movieId);

        when(reviewRepository.findByMovieId(movieId)).thenReturn(List.of(review));

        List<Review> result = reviewService.findByMovieId(movieId);

        assertThat(result).hasSize(1);
        verify(reviewRepository).findByMovieId(movieId);
    }
}