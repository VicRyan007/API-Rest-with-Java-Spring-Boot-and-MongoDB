package com.ifpe.project.services;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ifpe.project.domain.User;
import com.ifpe.project.dto.UserDTO;
import com.ifpe.project.repository.UserRepository;
import com.ifpe.project.services.exception.ObjectNotFoundException;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repo;

	public List<User> findAll(){
		return repo.findAll();
	}
	
	public User findById(String id) {
        // Verifica se o ID fornecido é um ObjectId válido
        if (!ObjectId.isValid(id)) {
            throw new IllegalArgumentException("ID inválido.");
        }

        // Busca o usuário no banco de dados pelo ID
        Optional<User> user = repo.findById(id);

        // Verifica se o usuário foi encontrado
        return user.orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado. ID: " + id));
    }
	
	public User insert(User obj) {
		return repo.insert(obj);
	}
	
	public User fromDTO(UserDTO objDto) {
		return new User(objDto.getId(),objDto.getName(),objDto.getEmail());
	}
	
}
