package com.ifpe.project.services;

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
	
		
}
