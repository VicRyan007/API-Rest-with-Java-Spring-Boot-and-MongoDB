package com.ifpe.project.dto;

import java.io.Serializable;
import java.util.Objects;

import com.ifpe.project.domain.User;

/**
 * DTO (Data Transfer Object) para representar um usuário.
 * Usado para transferir dados do usuário sem expor toda a entidade User.
 */
public class UserDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	private String email;
	
	/**
	 * Construtor padrão necessário para serialização.
	 */
	public UserDTO() {
	}
	
	/**
	 * Construtor que converte um User para UserDTO.
	 * 
	 * @param user Usuário a ser convertido para DTO
	 */
	public UserDTO(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.email = user.getEmail();
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		UserDTO other = (UserDTO) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return "UserDTO{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", email='" + email + '\'' +
				'}';
	}
}
