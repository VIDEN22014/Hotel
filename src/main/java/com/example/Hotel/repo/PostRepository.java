package com.example.Hotel.repo;

import com.example.Hotel.models.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {
}
