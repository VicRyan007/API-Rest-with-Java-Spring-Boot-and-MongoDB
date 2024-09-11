package com.ifpe.project.tests;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

import com.ifpe.project.domain.Post;
import com.ifpe.project.domain.User;
import com.ifpe.project.dto.UserDTO;
import com.ifpe.project.repository.UserRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserRequestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        User maria = new User(null, "Maria Brown", "maria@gmail.com");
        User alex = new User(null, "Alex Green", "alex@gmail.com");
        User bob = new User(null, "Bob Grey", "bob@gmail.com");
        userRepository.saveAll(List.of(maria, alex, bob));
    }

    @Test
    public void testGetAllUsers() {
        String url = "http://localhost:" + port + "/users";
        ResponseEntity<UserDTO[]> response = restTemplate.getForEntity(url, UserDTO[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(3, response.getBody().length);
    }

    @Test
    public void testGetUserById() {
        List<User> users = userRepository.findAll();
        User user = users.get(0);

        String url = "http://localhost:" + port + "/users/" + user.getId();
        ResponseEntity<User> response = restTemplate.getForEntity(url, User.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(user.getName(), response.getBody().getName());
    }

    @Test
    public void testCreateUser() {
        String url = "http://localhost:" + port + "/users";
        UserDTO newUserDto = new UserDTO(new User(null, "John Doe", "john.doe@gmail.com"));
        ResponseEntity<Void> response = restTemplate.postForEntity(url, newUserDto, Void.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getHeaders().getLocation());
        String createdUserUrl = response.getHeaders().getLocation().toString();

        ResponseEntity<User> createdUserResponse = restTemplate.getForEntity(createdUserUrl, User.class);
        assertEquals("John Doe", createdUserResponse.getBody().getName());
    }

    @Test
    public void testDeleteUser() {
        List<User> users = userRepository.findAll();
        User user = users.get(0);

        String url = "http://localhost:" + port + "/users/" + user.getId();
        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertFalse(userRepository.findById(user.getId()).isPresent());
    }

    @Test
    public void testUpdateUser() {
        List<User> users = userRepository.findAll();
        User maria = users.get(0);

        String url = "http://localhost:" + port + "/users/" + maria.getId();

        UserDTO updatedUserDto = new UserDTO(new User(null, "Maria Silva", "maria.silva@gmail.com"));
        HttpEntity<UserDTO> requestEntity = new HttpEntity<>(updatedUserDto);
        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Void.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        User updatedUser = userRepository.findById(maria.getId()).get();
        assertEquals("Maria Silva", updatedUser.getName());
    }

    @Test
    public void testFindUserPosts() {
        List<User> users = userRepository.findAll();
        User user = users.get(0);

        String url = "http://localhost:" + port + "/users/" + user.getId() + "/posts";
        ResponseEntity<Post[]> response = restTemplate.getForEntity(url, Post[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}
