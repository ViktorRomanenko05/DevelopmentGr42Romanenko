package de.ait.javalessons.service;

import de.ait.javalessons.model.Movie;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Slf4j
@Service
public class MovieDataService {

    private final File file = Path.of("src", "main", "resources", "movies_hw9.txt").toFile();

    private final Set<Movie> movies = readMoviesFromFile(file);

    //Метод для добавления фильма
    public boolean addMovie(Movie movie, Set<Movie> moviesSet, File fileMovies) {
        if (movie == null || movie.getTitle() == null || movie.getYear() == null || movie.getGenre() == null || movie.getId() == null) {
            log.warn("Input parameter is null");
            return false;
        }
        boolean isIdExist = moviesSet.stream().anyMatch(m -> m.getId().equals(movie.getId()));
        if (isIdExist) {
            log.info("Movie with id {} is already exist", movie.getId());
            return false;
        } else {
            boolean result = moviesSet.add(movie);
            if (!result) {
                log.info("Movie was not added");
            } else {
                log.info("Movie successfully added");
                writeMoviesToFile(fileMovies, moviesSet);
            }
            return result;
        }
    }

    //Метод для поиска фильма по ID
    public Movie findById(Long id, Set<Movie> moviesSet) {
        if (id == null) {
            log.info("Input parameter is null");
            return null;
        }
        Movie result = moviesSet.stream().filter(movie -> movie.getId().equals(id)).findFirst().orElse(null);
        if (result == null) {
            log.info("Movie with id {} was not found", id);
        } else {
            log.info("Movie {} with id {} was found", result.getTitle(), id);
        }
        return result;
    }

    //Метод для удаления фильма из списка по id
    public boolean removeMovieById(Long id, Set<Movie> moviesSet, File fileMovies) {
        Movie result = findById(id, moviesSet);
        boolean isRemoved = moviesSet.remove(result);
        if (isRemoved) {
            log.info("Movie with id {} was removed", id);
            writeMoviesToFile(fileMovies, moviesSet);
            return true;
        }
        else {return false;}
    }

    //Метод чтения фильмов из файла
    public Set<Movie> readMoviesFromFile(File file) {
        Set<Movie> resultSet = new HashSet<>();
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
                    resultSet.add(movie);
                } else {
                    log.error("Invalid movie data {}", line);
                }
            }
            log.info("movies parsed successfully");
        } catch (IOException exception) {
            log.error(exception.getMessage());
        }
        return resultSet;
    }

    //Метод для сортировки по названию в алфавитном порядке записи фильмов в файл
    public void writeMoviesToFile(File file, Set<Movie> moviesSet) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, false))) {
            List<Movie> moviesList = moviesSet.stream().sorted().toList();
            for (Movie movie : moviesList) {
                String line = String.format("%d, %s, %s, %s",
                        movie.getId(),
                        movie.getTitle(),
                        movie.getGenre(),
                        movie.getYear());
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }
            log.info("Movies was written successfully");
        } catch (IOException exception) {
            log.error("Error of writing movies to file: {}", exception.getMessage());
        }
    }

}
