package de.ait.javalessons.controller;

import de.ait.javalessons.model.Movie;
import de.ait.javalessons.service.MovieDataService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RestApiMovieControllerTest {

    private static MovieDataService movieDataService;
    private RestApiMovieController restApiMovieController;
    private static final Set<Movie> originalData = new HashSet<>();
    private final File testMovieSetupFile = Path.of("src", "test", "resources", "testMovieSetupFile.txt").toFile();

    //Так как приложение работает с файлами и сохраняет результаты работы -
    //на время выполнения тестов подменяем реальные данные на тестовые
    @BeforeAll
    public static void saveData() {
        movieDataService = new MovieDataService();
        originalData.addAll(movieDataService.getMovies());
    }

    @AfterAll
    public static void overTests() {
        movieDataService.getMovies().clear();
        movieDataService.getMovies().addAll(originalData);
        movieDataService.writeMoviesToFile(movieDataService.getFile(), movieDataService.getMovies());
    }

    @BeforeEach
    void setUp() {
        restApiMovieController = new RestApiMovieController(movieDataService);
        movieDataService.getMovies().clear();
        movieDataService.getMovies().addAll(movieDataService.readMoviesFromFile(testMovieSetupFile));
    }

    @Test
    @DisplayName("Get All Movies")
    void testGetAllMovies() {
        Iterable<Movie> resultIterable = restApiMovieController.getMovies();
        Set<Movie> resultSet = new HashSet<>();
        resultIterable.forEach(resultSet::add);
        assertEquals(10, resultSet.size());
    }


    @Test
    @DisplayName("Get Movie By Id Positive")
    void testGetMovieRyIdPositive() {
        Optional<Movie> result = restApiMovieController.getMovieById(8L);
        assertTrue(result.isPresent());
        assertEquals("Amadeus", result.get().getTitle());
    }

    @Test
    @DisplayName("Get Movie By Id Negative")
    void testGetMovieRyIdNegative() {
        Optional<Movie> result = restApiMovieController.getMovieById(11L);
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Add new movie")
    void testPostMovie() {
        Movie movieToAdd = new Movie(111L, "Terminator 3", "Action", 2003);
        restApiMovieController.postMovie(movieToAdd);
        assertEquals(11, movieDataService.getMovies().size());
        assertEquals("Terminator 3", movieDataService.getMovies().stream().filter(m -> m.getId() == 111).findFirst().get().getTitle());
    }

    @Test
    @DisplayName("Add new movie response test")
    void testPostMovieResponse() {
        Movie movieToAdd = new Movie(222L, "Spider man", "Action", 2002);
        ResponseEntity<Movie> responseEntityResult = restApiMovieController.postMovie(movieToAdd);
        assertEquals(201, responseEntityResult.getStatusCodeValue());
        assertEquals("Spider man", responseEntityResult.getBody().getTitle());
    }

    @Test
    @DisplayName("Add new movie negative response test")
    void testPostMovieNegativeResponse() {
        Movie movieToAdd = new Movie(221L, "The Great Beauty", "Drama", 2013);
        ResponseEntity<Movie> responseEntityResult = restApiMovieController.postMovie(movieToAdd);
        assertEquals(409, responseEntityResult.getStatusCodeValue());
        assertEquals("The Great Beauty", responseEntityResult.getBody().getTitle());
    }

    @Test
    @DisplayName("Delete movie success")
    void removeMovieById() {
        restApiMovieController.deleteMovie(5L);
        assertEquals(9, movieDataService.getMovies().size());
        assertFalse(movieDataService.getMovies().stream().anyMatch(m -> m.getId() == 5));
    }
}
