package de.ait.javalessons.lessons.lesson_3Tests;

import de.ait.javalessons.lessons.lesson_5.YouTubeAnalyzer;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class YouTubeAnalyzerTest {

    @org.junit.jupiter.api.Test
    void testIsMore10MSuccess() {

        YouTubeAnalyzer youTubeAnalyzer = new YouTubeAnalyzer();
         boolean result = youTubeAnalyzer.isMore10M();
        assertTrue(result);}
}
