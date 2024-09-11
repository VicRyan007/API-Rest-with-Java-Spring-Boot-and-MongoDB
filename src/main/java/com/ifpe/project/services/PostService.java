package com.ifpe.project.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ifpe.project.domain.Post;
import com.ifpe.project.repository.PostRepository;
import com.ifpe.project.services.exception.ObjectNotFoundException;

@Service
public class PostService {
	
	@Autowired
	private PostRepository repo;
	
    private static final Logger logger = LoggerFactory.getLogger(PostService.class);


	public List<Post> findAll(){
		return repo.findAll();
	}
	
	public Post findById(String id) {
        // Verifica se o ID fornecido é um ObjectId válido
        if (!ObjectId.isValid(id)) {
            throw new IllegalArgumentException("ID inválido.");
        }

        // Busca o usuário no banco de dados pelo ID
        Optional<Post> post = repo.findById(id);

        // Verifica se o usuário foi encontrado
        return post.orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado. ID: " + id));
    }
	
	 public List<Post> findByTitle(String text) {
	        try {
	            return repo.findByTitleContainingIgnoreCase(text);
	        } catch (Exception e) {
	            logger.error("Error occurred while finding posts by title", e);
	            throw e; // Re-throw or handle exception as needed
	        }
	    }
	
	public List<Post> fullSearch(String text, Date minDate, Date maxDate) {
		maxDate = new Date(maxDate.getTime() + 24 * 60 * 60 * 1000);
		return repo.fullSearch(text, minDate, maxDate);
	}
	
}
