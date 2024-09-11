package com.ifpe.project.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ifpe.project.domain.Post;
import com.ifpe.project.domain.User;
import com.ifpe.project.dto.AuthorDTO;
import com.ifpe.project.repository.PostRepository;
import com.ifpe.project.repository.UserRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostRequestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() throws Exception {
    	
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        postRepository.deleteAll();
        userRepository.deleteAll();
    	
        // Configura o ObjectMapper para o teste
        objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        
        // Criação de um usuário
        User maria = new User(null, "Maria Brown", "maria@gmail.com");
        userRepository.save(maria);
 
		// Criação de posts
        Post post1 = new Post(null, sdf.parse("21/03/2018"), "Partiu viagem", "Vou viajar para São Paulo. Abraços", new AuthorDTO(maria));
        Post post2 = new Post(null, sdf.parse("10/05/2022"), "Novo projeto", "Estamos começando um novo projeto na empresa!", new AuthorDTO(maria));

        postRepository.saveAll(List.of(post1, post2));
    }

    @Test
    public void testFindPostById() {
        List<Post> posts = postRepository.findAll();
        Post post = posts.get(0); // Pegamos o primeiro post

        String url = "http://localhost:" + port + "/posts/" + post.getId();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        // Use o ObjectMapper para converter a resposta em Post
        try {
            Post responsePost = objectMapper.readValue(response.getBody(), Post.class);
            assertEquals(post.getTitle(), responsePost.getTitle());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFindByTitle() {
        String url = "http://localhost:" + port + "/posts/titlesearch?text=projeto";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        // Use o ObjectMapper para converter a resposta em um array de Post
        try {
            Post[] posts = objectMapper.readValue(response.getBody(), Post[].class);
            assertEquals(1, posts.length); // Deve retornar 1 post com "projeto" no título
            assertEquals("Novo projeto", posts[0].getTitle());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFullSearch() {
        String url = "http://localhost:" + port + "/posts/fullsearch?text=viagem&minDate=01/01/2018&maxDate=31/12/2018";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        // Use o ObjectMapper para converter a resposta em um array de Post
        try {
            Post[] posts = objectMapper.readValue(response.getBody(), Post[].class);
            assertEquals(1, posts.length); // Deve retornar 1 post correspondente
            assertEquals("Partiu viagem", posts[0].getTitle());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
