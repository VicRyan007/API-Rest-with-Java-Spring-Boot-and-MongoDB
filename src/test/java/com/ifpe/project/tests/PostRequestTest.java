package com.ifpe.project.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ifpe.project.domain.Post;
import com.ifpe.project.domain.User;
import com.ifpe.project.dto.AuthorDTO;
import com.ifpe.project.dto.CommentDTO;
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
        Post post = posts.get(0);

        String url = "http://localhost:" + port + "/posts/" + post.getId();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        try {
            Post responsePost = objectMapper.readValue(response.getBody(), Post.class);
            assertEquals(post.getTitle(), responsePost.getTitle());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deserializar resposta", e);
        }
    }

    @Test
    public void testFindPostByIdNotFound() {
        String url = "http://localhost:" + port + "/posts/nonexistentid";
        assertThrows(HttpClientErrorException.NotFound.class, () -> {
            restTemplate.getForEntity(url, String.class);
        });
    }

    @Test
    public void testCreatePost() throws Exception {
        String url = "http://localhost:" + port + "/posts";
        User author = userRepository.findAll().get(0);
        
        Post newPost = new Post(null, new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2024"), 
            "Novo Post", "Conteúdo do novo post", new AuthorDTO(author));
        
        ResponseEntity<Void> response = restTemplate.postForEntity(url, newPost, Void.class);
        
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getHeaders().getLocation());
        
        String createdPostUrl = response.getHeaders().getLocation().toString();
        ResponseEntity<Post> createdPostResponse = restTemplate.getForEntity(createdPostUrl, Post.class);
        
        assertEquals("Novo Post", createdPostResponse.getBody().getTitle());
    }

    @Test
    public void testCreatePostWithInvalidData() {
        String url = "http://localhost:" + port + "/posts";
        Post invalidPost = new Post(null, null, "", "", null);
        
        assertThrows(HttpClientErrorException.BadRequest.class, () -> {
            restTemplate.postForEntity(url, invalidPost, Void.class);
        });
    }

    @Test
    public void testUpdatePost() throws Exception {
        List<Post> posts = postRepository.findAll();
        Post post = posts.get(0);
        
        String url = "http://localhost:" + port + "/posts/" + post.getId();
        
        Post updatedPost = new Post(null, post.getDate(), "Título Atualizado", 
            "Conteúdo atualizado", post.getAuthor());
        
        HttpEntity<Post> requestEntity = new HttpEntity<>(updatedPost);
        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Void.class);
        
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        
        ResponseEntity<Post> updatedPostResponse = restTemplate.getForEntity(url, Post.class);
        assertEquals("Título Atualizado", updatedPostResponse.getBody().getTitle());
    }

    @Test
    public void testDeletePost() {
        List<Post> posts = postRepository.findAll();
        Post post = posts.get(0);
        
        String url = "http://localhost:" + port + "/posts/" + post.getId();
        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);
        
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertThrows(HttpClientErrorException.NotFound.class, () -> {
            restTemplate.getForEntity(url, Post.class);
        });
    }

    @Test
    public void testAddComment() throws Exception {
        List<Post> posts = postRepository.findAll();
        Post post = posts.get(0);
        
        String url = "http://localhost:" + port + "/posts/" + post.getId() + "/comments";
        CommentDTO comment = new CommentDTO("Ótimo post!", new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2024"), 
            new AuthorDTO(userRepository.findAll().get(0)));
        
        ResponseEntity<Void> response = restTemplate.postForEntity(url, comment, Void.class);
        
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        
        ResponseEntity<Post> updatedPostResponse = restTemplate.getForEntity(
            "http://localhost:" + port + "/posts/" + post.getId(), Post.class);
        assertEquals(1, updatedPostResponse.getBody().getComments().size());
    }

    @Test
    public void testFindByTitle() {
        String url = "http://localhost:" + port + "/posts/titlesearch?text=projeto";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        try {
            Post[] posts = objectMapper.readValue(response.getBody(), Post[].class);
            assertEquals(1, posts.length);
            assertEquals("Novo projeto", posts[0].getTitle());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deserializar resposta", e);
        }
    }

    @Test
    public void testFullSearch() {
        String url = "http://localhost:" + port + "/posts/fullsearch?text=viagem&minDate=01/01/2018&maxDate=31/12/2018";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        try {
            Post[] posts = objectMapper.readValue(response.getBody(), Post[].class);
            assertEquals(1, posts.length);
            assertEquals("Partiu viagem", posts[0].getTitle());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deserializar resposta", e);
        }
    }

    @Test
    public void testFullSearchWithInvalidDates() {
        String url = "http://localhost:" + port + "/posts/fullsearch?text=viagem&minDate=invalid&maxDate=invalid";
        assertThrows(HttpClientErrorException.BadRequest.class, () -> {
            restTemplate.getForEntity(url, String.class);
        });
    }
}
