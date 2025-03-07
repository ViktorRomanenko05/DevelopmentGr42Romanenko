package de.ait.javalessons.model;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Getter

public class Movie implements Comparable <Movie> {

    Long id;
    String title;
    String genre;
    Year year;

    //Конструктор с автоматическим заданием ID
//    public Movie(String title, String genre, int year) {
//        this.id = Long.parseLong(generateId());
//        this.title = title;
//        this.genre = genre;
//        this.year = Year.of(year);
//    }


    //Идея была в том, чтобы генерировать id, если он не передан в запросе.
    //Перегрузка конструкторов не сработала при работе с запросами.
    //Поэтому в целях тренировки такой способ:
    public Movie(Long id, String title, String genre, int year) {
        if(id == null){this.id = Long.parseLong(generateId());}
        else{this.id = id;}
        this.title = title;
        this.genre = genre;
        this.year = Year.of(year);
    }

    //Метод для генерации ID
    private String generateId() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        return LocalDateTime.now().format(formatter);
    }

    //Генерируем equals и hashcode по всем параметрам за исключением id
    //Таким образом избегаем возможности добавления одинаковых фильмов с разными id
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(title, movie.title) && Objects.equals(genre, movie.genre) && Objects.equals(year, movie.year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, genre, year);
    }

    //Переопределяем метод compareTo для сортировки по умолчанию в алфавитном порядке
    @Override
    public int compareTo(Movie otherMovie) {
        return this.title.compareTo(otherMovie.title);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", year=" + year +
                '}';
    }
}
