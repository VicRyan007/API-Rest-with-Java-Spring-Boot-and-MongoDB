package com.ifpe.project.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.ifpe.project.domain.Post;

/**
 * Interface de repositório para a entidade Post.
 * Fornece operações básicas de CRUD e consultas personalizadas para posts.
 */
@Repository
public interface PostRepository extends MongoRepository<Post, String> {

	/**
	 * Busca posts pelo título usando expressão regular.
	 * 
	 * @param text Texto para busca no título
	 * @return Lista de posts encontrados
	 */
	@Query("{'title': { $regex: ?0, $options: 'i' } }")
	List<Post> searchTitle(String text);
	
	/**
	 * Busca posts pelo título ignorando maiúsculas e minúsculas.
	 * 
	 * @param text Texto para busca no título
	 * @return Lista de posts encontrados
	 */
	List<Post> findByTitleContainingIgnoreCase(String text);
	
	/**
	 * Realiza uma busca completa em posts.
	 * Busca por texto no título, corpo e comentários, dentro de um período específico.
	 * 
	 * @param text Texto para busca
	 * @param minDate Data inicial do período
	 * @param maxDate Data final do período
	 * @return Lista de posts encontrados
	 */
	@Query("{ $and: [ { date: {$gte: ?1} }, { date: { $lte: ?2} } , " +
		   "{ $or: [ { 'title': { $regex: ?0, $options: 'i' } }, " +
		   "{ 'body': { $regex: ?0, $options: 'i' } }, " +
		   "{ 'comments.text': { $regex: ?0, $options: 'i' } } ] } ] }")
	List<Post> fullSearch(String text, Date minDate, Date maxDate);
}
