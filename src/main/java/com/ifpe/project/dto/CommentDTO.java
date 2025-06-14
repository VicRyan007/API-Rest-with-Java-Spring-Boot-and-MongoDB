package com.ifpe.project.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * DTO (Data Transfer Object) para representar um comentário.
 * Usado para transferir dados do comentário sem expor toda a entidade Comment.
 */
public class CommentDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String text;
	private Date date;
	private AuthorDTO author;
	
	/**
	 * Construtor padrão necessário para serialização.
	 */
	public CommentDTO() {
	}

	/**
	 * Construtor com parâmetros.
	 * 
	 * @param text Texto do comentário
	 * @param date Data do comentário
	 * @param author Autor do comentário
	 */
	public CommentDTO(String text, Date date, AuthorDTO author) {
		this.text = text;
		this.date = date;
		this.author = author;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public AuthorDTO getAuthor() {
		return author;
	}

	public void setAuthor(AuthorDTO author) {
		this.author = author;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		CommentDTO other = (CommentDTO) obj;
		return Objects.equals(text, other.text) &&
			   Objects.equals(date, other.date) &&
			   Objects.equals(author, other.author);
	}

	@Override
	public int hashCode() {
		return Objects.hash(text, date, author);
	}

	@Override
	public String toString() {
		return "CommentDTO{" +
				"text='" + text + '\'' +
				", date=" + date +
				", author=" + author +
				'}';
	}
}
