package de.ait.javalessons.service;

import de.ait.javalessons.model.Movie;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class MovieDataServiceTest {

    private static MovieDataService movieDataService;
    //Тестовый Set для хранения фильмов прочитанных из файла
    private static Set<Movie> testDataSet = new HashSet<>();
    //Тестовый List для контроля записи в файл и порядка сортировки
    private List<Movie> testDataOutputList = new ArrayList<>();
    private final File testMovieSetupFile = Path.of("src", "test", "resources", "testMovieSetupFile.txt").toFile();
    private final File testMovieOutputFile = Path.of("src", "test", "resources", "testMovieOutputFile.txt").toFile();

    Movie movie1 = new Movie(null, "The Revenant", "Drama", 2015);
    Movie movie2 = new Movie(222L, null, "Drama", 2014);
    Movie movie3 = new Movie(333L, "The Social Network", null, 2010);
    Movie movie4 = new Movie(444L, "Parasite", "Thriller", 2019);
    Movie movie5 = new Movie(555L, "Parasite", "Thriller", 2019);
    Movie movie6 = null;

    @BeforeEach
    void setUp() {
        movieDataService = new MovieDataService();
        testDataSet.clear();
        testDataSet = movieDataService.readMoviesFromFile(testMovieSetupFile);
        testDataOutputList.clear();
        clearOutputFile();
    }

    @Test
    @DisplayName("Read movies from file")
    void testReadMoviesFromFile() {
        assertEquals(10, testDataSet.size());
    }

    @Test
    @DisplayName("Write movies to file")
    void testWriteMoviesToFile() {
        movieDataService.writeMoviesToFile(testMovieOutputFile, testDataSet);
        testDataOutputList = readMoviesFromFile(testMovieOutputFile);
        assertEquals(8, testDataOutputList.get(0).getId());
        assertEquals(7, testDataOutputList.get(3).getId());
        assertEquals(10, testDataOutputList.get(7).getId());
        assertEquals(4, testDataOutputList.get(9).getId());
    }

    @Test
    @DisplayName("Adding null object to list")
    void testAddNullMovieToList() {
        assertFalse(movieDataService.addMovie(movie6, testDataSet, testMovieOutputFile));
        assertEquals(10, testDataSet.size());
        testDataOutputList = readMoviesFromFile(testMovieOutputFile);
        assertFalse(testDataOutputList.contains(null));
    }

    @Test
    @DisplayName("Adding similar movies to list")
    void testAddMovieToList() {

        assertTrue(movieDataService.addMovie(movie4, testDataSet, testMovieOutputFile));
        assertFalse(movieDataService.addMovie(movie5, testDataSet, testMovieOutputFile));

        assertEquals(11, testDataSet.size());
        testDataOutputList = readMoviesFromFile(testMovieOutputFile);
        assertTrue(testDataOutputList.contains(movie4));
    }

    @Test
    @DisplayName("Adding movies to list with null ID")
    void testAddMovieToListNullId() {
        assertTrue(movieDataService.addMovie(movie1, testDataSet, testMovieOutputFile));
        assertEquals(11, testDataSet.size());
        testDataOutputList = readMoviesFromFile(testMovieOutputFile);
        assertTrue(testDataOutputList.contains(movie1));
    }

    @Test
    @DisplayName("Adding movies to list with null parameters")
    void testAddMovieToListNullParameters() {
        assertFalse(movieDataService.addMovie(movie2, testDataSet, testMovieOutputFile));
        assertFalse(movieDataService.addMovie(movie3, testDataSet, testMovieOutputFile));
        assertEquals(10, testDataSet.size());
        testDataOutputList = readMoviesFromFile(testMovieOutputFile);
        assertFalse(testDataOutputList.contains(movie2));
        assertFalse(testDataOutputList.contains(movie3));
    }

    @Test
    @DisplayName("FindMovieByIdPositive")
    void testFindByIdPositive() {
        assertEquals("Amadeus", movieDataService.findById(8L, testDataSet).getTitle());
    }

    @Test
    @DisplayName("FindMovieByIdNegative")
    void testFindByIdNegative() {
        assertNull(movieDataService.findById(101010L, testDataSet));
    }

    @Test
    @DisplayName("RemoveMovieByIdPositive")
    void testRemoveByIdPositive() {
        movieDataService.writeMoviesToFile(testMovieOutputFile, testDataSet);
        movieDataService.removeMovieById(5L, testDataSet, testMovieOutputFile);
        testDataOutputList = readMoviesFromFile(testMovieOutputFile);
        assertEquals(9, testDataSet.size());
        assertFalse(testDataOutputList.stream().anyMatch(movie -> movie.getId() == (long) 5));
    }

    @Test
    @DisplayName("RemoveMovieByIdNegative")
    void testRemoveByIdNegative() {
        movieDataService.writeMoviesToFile(testMovieOutputFile, testDataSet);
        movieDataService.removeMovieById(5000L, testDataSet, testMovieOutputFile);
        testDataOutputList = readMoviesFromFile(testMovieOutputFile);
        assertEquals(10, testDataSet.size());
    }

    //Метод для очистки файла с результатами работы теста
    private void clearOutputFile() {
        try (FileWriter writer = new FileWriter(testMovieOutputFile, false)) {
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Метод для чтения из файла
    private List<Movie> readMoviesFromFile(File file) {
        List<Movie> resultList = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] movieData = line.split(",");
                if (movieData.length == 4) {
                    Long id = Long.parseLong(movieData[0].trim());
                    String title = movieData[1].trim();
                    String genre = movieData[2].trim();
                    int year = Integer.parseInt(movieData[3].trim());
                    Movie movie = new Movie(id, title, genre, year);
                    resultList.add(movie);
                } else {
                    log.error("Invalid movie data {}", line);
                }
            }
            log.info("movies parsed successfully");
        } catch (IOException exception) {
            log.error(exception.getMessage());
        }
        return resultList;
    }

}
