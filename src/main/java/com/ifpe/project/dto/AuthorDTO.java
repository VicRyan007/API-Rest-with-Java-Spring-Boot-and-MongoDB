package com.ifpe.project.dto;

import java.io.Serializable;
import java.util.Objects;

import com.ifpe.project.domain.User;

/**
 * DTO (Data Transfer Object) para representar um autor.
 * Usado para transferir dados do autor sem expor toda a entidade User.
 */
public class AuthorDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	
	/**
	 * Construtor padrão necessário para serialização.
	 */
	public AuthorDTO() {
	}
	
	/**
	 * Construtor que converte um User para AuthorDTO.
	 * 
	 * @param user Usuário a ser convertido para DTO
	 */
	public AuthorDTO(User user) {
		this.id = user.getId();
		this.name = user.getName();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		AuthorDTO other = (AuthorDTO) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return "AuthorDTO{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				'}';
	}
}
