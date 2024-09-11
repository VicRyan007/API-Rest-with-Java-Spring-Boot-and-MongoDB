package com.ifpe.project.resources;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ifpe.project.domain.Post;
import com.ifpe.project.resources.util.URL;
import com.ifpe.project.services.PostService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping(value = "/posts")
public class PostResource {

    @Autowired
    private PostService service;

    private static final Logger logger = LoggerFactory.getLogger(PostResource.class);

    @Operation(summary = "Get post by ID", description = "Retrieve a single post by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Post retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Post not found")
    })
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<Post> findById(
        @PathVariable String id) {
        Post obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @Operation(summary = "Search posts by title", description = "Search posts based on title text")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Posts retrieved successfully"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @RequestMapping(value="/titlesearch", method=RequestMethod.GET)
    public ResponseEntity<List<Post>> findByTitle(
        @RequestParam(value="text", defaultValue="") String text) {
        try {
            text = URL.decodeParam(text);
            List<Post> list = service.findByTitle(text);
            return ResponseEntity.ok().body(list);
        } catch (Exception e) {
            logger.error("Error occurred while searching by title", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Full search posts", description = "Search posts based on text and date range")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Posts retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @RequestMapping(value = "/fullsearchByResource", method = RequestMethod.GET)
    public ResponseEntity<List<Post>> fullSearch(
        @RequestParam(value = "text", defaultValue = "") String text,
        @RequestParam(value = "minDate", defaultValue = "") String minDate,
        @RequestParam(value = "maxDate", defaultValue = "") String maxDate) {

        Date min = URL.convertDate(minDate, new Date(0L));
        Date max = URL.convertDate(maxDate, new Date());
        List<Post> list = service.fullSearch(text, min, max);
        return ResponseEntity.ok().body(list);
    }
}
