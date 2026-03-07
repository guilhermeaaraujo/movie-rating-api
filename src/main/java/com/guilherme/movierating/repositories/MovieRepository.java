package com.guilherme.movierating.repositories;

import com.guilherme.movierating.model.entities.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends MongoRepository<Movie, String> {
}
