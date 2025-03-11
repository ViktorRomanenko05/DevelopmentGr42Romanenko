package de.ait.javalessons.controller;

import de.ait.javalessons.model.Movie;
import de.ait.javalessons.services.MovieDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/movies")
public class RestApiMovieController {
    private final MovieDataService movieDataService;

    //Внедряем зависимость MovieDataService в контроллер
    @Autowired
    public RestApiMovieController(MovieDataService movieDataService) {
        this.movieDataService = movieDataService;
    }

    /**
     * Метод для получения всех фильмов.
     * GET-запрос на /movies
     *
     * @return список всех фильмов
     */
    @GetMapping
    Iterable<Movie> getMovies() {
        return movieDataService.getMovies();
    }

    /**
     * Метод для получения фильма по его ID.
     * GET-запрос на /movies/{id}
     *
     * @param id идентификатор фильма
     * @return Movie, если фильм не найден - Optional.empty
     */
    @GetMapping("/{id}")
    Optional<Movie> getMovieById(@PathVariable Long id) {
        return Optional.ofNullable(movieDataService.findById(id, movieDataService.getMovies()));
    }

    /**
     * Метод для добавления нового фильма.
     * POST-запрос на /movies
     *
     * @param movie объект фильма, переданный в теле запроса
     * @return добавленный фильм
     */
    @PostMapping
    ResponseEntity<Movie> postMovie(@RequestBody Movie movie) {
        return (movieDataService.addMovie(movie, movieDataService.getMovies(), movieDataService.getFile())) ?
                new ResponseEntity<>(movie, HttpStatus.CREATED) :
                new ResponseEntity<>(movie, HttpStatus.CONFLICT);
    }

    /**
     * Метод для удаления фильма по его ID.
     * DELETE-запрос на /movies/{id}
     *
     * @param id идентификатор фильма, который нужно удалить
     */
    @DeleteMapping("/{id}")
    void deleteMovie(@PathVariable Long id) {
        movieDataService.removeMovieById(id, movieDataService.getMovies(), movieDataService.getFile());
    }

}
