package de.ait.javalessons.lessons.lesson_3Tests;

import de.ait.javalessons.lessons.lesson_3.YouTubeAnalyzer;
import de.ait.javalessons.lessons.lesson_3.YoutubeVideo;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class YouTubeAnalyzerTest {

    //@org.junit.jupiter.api.Test
    //void testIsMore10MSuccess() {

        //YouTubeAnalyzer youTubeAnalyzer = new YouTubeAnalyzer();
        // boolean result = YouTubeAnalyzer.isMore10M();
        //assertTrue(result);}

    @Test
    void testIsMore10M_WhenVideoHasMoreThan10MViews() {
        // Arrange
        List<YoutubeVideo> testVideos = List.of(
                new YoutubeVideo("Популярное видео", "Top Channel", 15_000_000, 12000, 720, "Образование", true),
                new YoutubeVideo("Обычное видео", "Regular Channel", 500_000, 8000, 600, "Спорт", false)
        );
        YouTubeAnalyzer.videos = testVideos;

        // Act
        boolean result = YouTubeAnalyzer.isMore10M();

        // Assert
        assertTrue(result);
    }

    @Test
    void testIsMore10M_WhenNoVideoHasMoreThan10MViews() {
        // Arrange
        List<YoutubeVideo> testVideos = List.of(
                new YoutubeVideo("Обычное видео", "Regular Channel", 500_000, 8000, 600, "Спорт", false),
                new YoutubeVideo("Ещё одно обычное видео", "Another Channel", 800_000, 10000, 1200, "Кулинария", false)
        );
        YouTubeAnalyzer.videos = testVideos;

        // Act
        boolean result = YouTubeAnalyzer.isMore10M();

        // Assert
        assertFalse(result);
    }

}
