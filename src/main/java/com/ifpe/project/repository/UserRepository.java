package com.ifpe.project.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ifpe.project.domain.User;

/**
 * Interface de repositório para a entidade User.
 * Fornece operações básicas de CRUD e consultas personalizadas.
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {
    // Métodos personalizados podem ser adicionados aqui
}
