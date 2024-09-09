package com.ifpe.project.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ifpe.project.domain.Post;

@Repository
public interface PostRepository extends MongoRepository<Post,String>{

	List<Post> findByTitleContainingIgnoreCase(String text);

}
