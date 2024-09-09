package com.ifpe.project.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.ifpe.project.domain.Post;
import com.ifpe.project.domain.User;
import com.ifpe.project.dto.AuthorDTO;
import com.ifpe.project.repository.PostRepository;
import com.ifpe.project.repository.UserRepository;

@Configuration
public class Instantiation implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		userRepository.deleteAll();
		postRepository.deleteAll();
		
		User maria = new User(null, "Maria Brown", "maria@gmail.com");
		User alex = new User(null, "Alex Green", "alex@gmail.com");
		User bob = new User(null, "Bob Grey", "bob@gmail.com");
		
		
		
		userRepository.saveAll(Arrays.asList(maria,alex,bob));
		
		Post post1 = new Post(null, sdf.parse("21/03/2018"), "Partiu viagem", "Vou viajar para São Paulo. Abraços", new AuthorDTO(maria));
		Post post2 = new Post(null, sdf.parse("10/05/2022"), "Novo projeto", "Estamos começando um novo projeto na empresa!", new AuthorDTO(maria));
		Post post3 = new Post(null, sdf.parse("15/07/2023"), "Aniversário", "Hoje é meu aniversário! Vamos comemorar.", new AuthorDTO(maria));
		Post post4 = new Post(null, sdf.parse("03/11/2024"), "Evento especial", "Não perca nosso evento especial no próximo mês.", new AuthorDTO(bob));
		Post post5 = new Post(null, sdf.parse("25/12/2021"), "Feliz Natal", "Desejo a todos um Feliz Natal e um próspero Ano Novo.", new AuthorDTO(bob));
		Post post6 = new Post(null, sdf.parse("04/09/2020"), "Nova atualização", "Lançamos uma nova atualização do nosso software.", new AuthorDTO(alex));
		Post post7 = new Post(null, sdf.parse("30/06/2019"), "Desafio concluído", "Concluímos com sucesso o desafio de codificação.", new AuthorDTO(alex));
		Post post8 = new Post(null, sdf.parse("12/01/2023"), "Início do ano", "Começando o ano com novas metas e objetivos.", new AuthorDTO(alex));	
		
		postRepository.saveAll(Arrays.asList(post1,post2,post3,post4,post5,post6,post7,post8));
	}

}
