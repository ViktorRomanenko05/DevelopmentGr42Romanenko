package de.ait.javalessons.homeworksTests.homework5Tests;

import de.ait.javalessons.homeworks.homework_5.GeoDataProcessor;
import de.ait.javalessons.homeworks.homework_5.Operations;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GeoDataProcessorTests {

    //Arrange
    private final List<String> testWords = Arrays.asList("Argentina", "Armenia", "Australia", "Chad", "Poland", "Brazil", "Belgium", "Mexico", "Morocco", "Turkey", "", null);
    private final List<String> blankList = new ArrayList<>();

    @Test
    void testStartsWithLetterTwoWasFound() {
        //Action
        List<String> resultList = GeoDataProcessor.startsWithLetter(testWords, "B");
        //Assert
        assertEquals(2, resultList.size());
        assertEquals("Brazil", resultList.get(0));
        assertEquals("Belgium", resultList.get(1));
    }

    @Test
    void testStartsWithLetterWasNotFound() {
        //Action
        List<String> resultList = GeoDataProcessor.startsWithLetter(blankList, "a");
        //Assert
        assertEquals(0, resultList.size());
    }

    @Test
    void testStartsWithLetterNullInput() {
        //Action
        List<String> resultList = GeoDataProcessor.startsWithLetter(null, "a");
        //Assert
        assertTrue(resultList.isEmpty());
    }

    @Test
    void testEndsWithLetterTwoWasFound() {
        //Action
        List<String> resultList = GeoDataProcessor.endsWithLetter(testWords, "o");
        //Assert
        assertEquals(2, resultList.size());
        assertEquals("Mexico", resultList.get(0));
        assertEquals("Morocco", resultList.get(1));
    }

    @Test
    void testEndsWithLetterWasNotFound() {
        //Action
        List<String> resultList = GeoDataProcessor.endsWithLetter(blankList, "a");
        //Assert
        assertTrue(resultList.isEmpty());
    }

    @Test
    void testFilteringByNameLess() {
        //Action
        List<String> resultList = GeoDataProcessor.filteringByNameLength(testWords, Operations.LESS, 5);
        //Assert
        assertEquals(1, resultList.size());
        assertEquals("Chad", resultList.get(0));
    }

    @Test
    void testFilteringByNameLessOrEqual() {
        //Action
        List<String> resultList = GeoDataProcessor.filteringByNameLength(testWords, Operations.LESS_OR_EQUAL, 5);
        //Assert
        assertEquals(1, resultList.size());
        assertEquals("Chad", resultList.get(0));
    }

    @Test
    void testFilteringByNameEqual() {
        //Action
        List<String> resultList = GeoDataProcessor.filteringByNameLength(testWords, Operations.EQUAL, 7);
        //Assert
        assertEquals(3, resultList.size());
        assertEquals("Armenia", resultList.get(0));
        assertEquals("Belgium", resultList.get(1));
        assertEquals("Morocco", resultList.get(2));
    }

    @Test
    void testFilteringByNameGreaterOrEqual() {
        //Action
        List<String> resultList = GeoDataProcessor.filteringByNameLength(testWords, Operations.GREATER_OR_EQUAL, 8);
        //Assert
        assertEquals(2, resultList.size());
        assertEquals("Argentina", resultList.get(0));
        assertEquals("Australia", resultList.get(1));
    }

    @Test
    void testFilteringByNameGreater() {
        //Action
        List<String> resultList = GeoDataProcessor.filteringByNameLength(testWords, Operations.GREATER, 8);
        //Assert
        assertEquals(2, resultList.size());
        assertEquals("Argentina", resultList.get(0));
        assertEquals("Australia", resultList.get(1));
    }

    @Test
    void testFilteringByNameNotFound() {
        //Action
        List<String> resultList = GeoDataProcessor.filteringByNameLength(blankList, Operations.GREATER, 2);
        //Assert
        assertTrue(resultList.isEmpty());
    }

    @Test
    void testFilteringByEvenLettersCountNotEmpty() {
        //Action
        List<String> resultList = GeoDataProcessor.filteringByEvenLetterCount(testWords);
        //Assert
        assertEquals(5, resultList.size());
        assertEquals("Chad", resultList.get(0));
        assertEquals("Poland", resultList.get(1));
        assertEquals("Brazil", resultList.get(2));
        assertEquals("Mexico", resultList.get(3));
        assertEquals("Turkey", resultList.get(4));
    }

    @Test
    void testFilteringByEvenLettersCountEmpty() {
        //Action
        List<String> resultList = GeoDataProcessor.filteringByEvenLetterCount(blankList);
        //Assert
        assertTrue(resultList.isEmpty());
    }

    @Test
    void testFilteringNamesWithSpecifiedLetterFound() {
        //Action
        List<String> resultList = GeoDataProcessor.filteringNamesWithSpecifiedLetter(testWords, "m");
        //Assert
        assertEquals(2, resultList.size());
        assertEquals("Armenia", resultList.get(0));
        assertEquals("Belgium", resultList.get(1));
    }

    @Test
    void testFilteringNamesWithSpecifiedLetterNotFound() {
        //Action
        List<String> resultList = GeoDataProcessor.filteringNamesWithSpecifiedLetter(blankList, "x");
        //Assert
        assertTrue(resultList.isEmpty());
    }
}
