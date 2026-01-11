package com.svalero.cityEvents.repository;

import com.svalero.cityEvents.domain.Review;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long> {

    List<Review> findAll();
    List<Review> findByUserUsername(String username);
}
