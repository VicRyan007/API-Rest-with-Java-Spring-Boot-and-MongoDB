package com.ifpe.project.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.ifpe.project.dto.AuthorDTO;
import com.ifpe.project.dto.CommentDTO;

/**
 * Representa um post no sistema.
 * Esta classe é mapeada para a coleção "posts" no MongoDB.
 */
@Document(collection = "posts")
public class Post implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	private Date date;
	private String title;
	private String body;
	private AuthorDTO author;
	
	private List<CommentDTO> comments = new ArrayList<>(); 
	
	/**
	 * Construtor padrão necessário para o MongoDB.
	 */
	public Post() {
	}

	/**
	 * Construtor com parâmetros.
	 * 
	 * @param id Identificador único do post
	 * @param date Data de criação do post
	 * @param title Título do post
	 * @param body Conteúdo do post
	 * @param author Autor do post
	 */
	public Post(String id, Date date, String title, String body, AuthorDTO author) {
		this.id = id;
		this.date = date;
		this.title = title;
		this.body = body;
		this.author = author;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public AuthorDTO getAuthor() {
		return author;
	}
	
	public void setAuthor(AuthorDTO author) {
		this.author = author;
	}
	
	public List<CommentDTO> getComments() {
		return comments;
	}
	
	public void setComments(List<CommentDTO> comments) {
		this.comments = comments;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Post other = (Post) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Post{" +
				"id='" + id + '\'' +
				", date=" + date +
				", title='" + title + '\'' +
				", author=" + author +
				'}';
	}
}
