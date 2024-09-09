package com.ifpe.project.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ifpe.project.domain.User;

@Repository
public interface UserRepository extends MongoRepository<User,String>{

	

}
