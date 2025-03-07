package de.ait.javalessons.controller;

import de.ait.javalessons.model.Movie;
import de.ait.javalessons.services.MovieDataService;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RestApiMovieControllerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private MovieDataService movieDataService;

    private static final String BASE_URL = "/movies";

    //Определим порядок выполнения тестов таким образом, чтобы
    //созданный в тесте #2 объект удалялся в тесте #5

    @Test
    @Order(1)
    @DisplayName("GetMovies")
    void testGetMoviesReturnDefaultMovies() {
        ResponseEntity<Movie[]> response = testRestTemplate.getForEntity(BASE_URL, Movie[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(10, response.getBody().length);
    }

    @Test
    @Order(2)
    @DisplayName("PostMovie")
    void testPostMovieAddNewMovie() {
        Movie movieToAdd = new Movie(123L, "Avatar", "Action", 2009);
        ResponseEntity<Movie> response = testRestTemplate.postForEntity(BASE_URL, movieToAdd, Movie.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Avatar", response.getBody().getTitle());
        assertEquals(123, response.getBody().getId());
    }

    @Test
    @Order(3)
    @DisplayName("GetMovieByIdWasFound")
    void testGetMovieByIdWasFound() {
        ResponseEntity<Movie> response = testRestTemplate.getForEntity(BASE_URL + "/123", Movie.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Avatar", response.getBody().getTitle());
        assertEquals(123, response.getBody().getId());
    }

    @Test
    @Order(4)
    @DisplayName("GetMovieByIdWasNotFound")
    void testGetMovieByIdWasNotFound() {
        ResponseEntity<Movie> response = testRestTemplate.getForEntity(BASE_URL + "/50", Movie.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    @Order(5)
    @DisplayName("DeleteMovieById")
    void testDeleteMovie() {
        testRestTemplate.delete(BASE_URL + "/123");
        assertEquals(10, movieDataService.getMovies().size());
    }
}
