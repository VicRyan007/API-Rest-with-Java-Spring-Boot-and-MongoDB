package com.ifpe.project.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ifpe.project.domain.Post;
import com.ifpe.project.repository.PostRepository;
import com.ifpe.project.services.exception.ObjectNotFoundException;

@Service
public class PostService {
	
	@Autowired
	private PostRepository repo;

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
	
	public List<Post> findByTitle (String text){
		return repo.searchTitle(text);
	}
	
	public List<Post> fullSearch(String text, Date minDate, Date maxDate) {
		maxDate = new Date(maxDate.getTime() + 24 * 60 * 60 * 1000);
		return repo.fullSearch(text, minDate, maxDate);
	}
	
}
