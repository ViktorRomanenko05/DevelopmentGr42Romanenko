package de.ait.javalessons.homeworksTests.homework7;

import de.ait.javalessons.homeworks.homework_7.Calculations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CalculationsTests {

    private Calculations calculations;

    @BeforeEach
    public void setUp() {
        calculations = new Calculations();
    }

    //Task 1
    //Testing the Addition Method
    //Write a parameterized test for the add(int a, int b) method,
    //which takes two integers and returns their sum.

    //2. Write a parameterized test in JUnit 5.
    //3. Use the @ParameterizedTest annotation and @CsvSource.
    //4. Verify the correctness of calculations with various input data.

    @ParameterizedTest
    @DisplayName("Task 1 test")
    @CsvSource({
            "2, 2, 4",
            "3, 1, 4",
            "0, 1, 1",
            "1, 0, 1",
            "0, 0, 0",
            "-1, -2, -3",
            "-1, 2, 1",
            "1, -2, -1"
    })
    void testAddition(int a, int b, int expectedResult) {
        assertEquals(expectedResult, calculations.addition(a, b));
    }

    //Task 2
    //Checking if a Number is Even
    //Write a parameterized test for the isEven(int number) method,
    //which checks if a number is even.

    //2. Write a parameterized test in JUnit 5.
    //3. Use the @ParameterizedTest annotation and @ValueSource.
    //4. Verify that the method correctly determines even numbers.

    @ParameterizedTest
    @DisplayName("Task 2 positive assertion")
    @ValueSource(ints = {0, 2, 8, 66, -6, -98})
    void testIsEvenPositiveResult(int number) {
        assertTrue(calculations.isEven(number));
    }

    @ParameterizedTest
    @DisplayName("Task 2 negative assertion")
    @ValueSource(ints = {1, 3, 9, 65, -7, -99})
    void testIsEvenNegativeResult(int number) {
        assertFalse(calculations.isEven(number));
    }

    //Task 3
    //Testing Division Method Correctness
    //Write a parameterized test for the divide(int a, int b) method,
    //which performs division of two numbers and returns the result.

    //Write a parameterized test in JUnit 5.
    //Use @CsvSource to check various input sets.
    //Verify the correctness of calculations and handle division by zero.
    @ParameterizedTest
    @DisplayName("Task 3 test")
    @CsvSource({
            "2, 2, 1",
            "88, 8, 11",
            "1, 2, 0",
            "0, 5, 0",
            "5, 0, 0"
    })
    void testDivide(int a, int b, int expectedResult) {
        assertEquals(expectedResult, calculations.divide(a, b));
    }

    //Task 4
    //Checking String Length
    //Write a parameterized test for the getLength(String str) method,
    //which takes a string and returns its length.

    //2. Write a parameterized test in JUnit 5.
    //3. Use @CsvSource to pass strings of different lengths.
    //4. Verify the correctness of length calculation, including empty and
    //null strings.

    @ParameterizedTest
    @DisplayName("Task 4 test exclude null")
    @CsvSource({
            "'One', 3",
            "'Test', 4",
            "'With space', 10",
            "' spaceBefore', 12",
            "'SpaceAfter ', 11",
            "' ', 1",
            "'', 0"
    })
    void testGetLengthWithoutNull(String text, int expectedResult) {
        assertEquals(expectedResult, calculations.getLength(text));
    }

    @Test
    @DisplayName("Task 4 test with null")
    void testGetLengthWithNull() {
        assertEquals(-1, calculations.getLength(null));
    }

    //Task 5
    //Checking if a String Contains a Specific Word
    //Write a parameterized test for the containsWord(String text, String word) method,
    //which checks if a string contains a given word.

    //1. Write a parameterized test in JUnit 5.
    //2. Use @CsvSource to pass different combinations of text and word.
    //3. Verify that the method correctly determines word presence in a string.

    @ParameterizedTest
    @DisplayName("Task 5 test exclude null parameters")
    @CsvSource({
            "'One, Two, Three', 'Two', true",
            "'One, Two, Three', 'two', false",
            "'One, Two, Three', ' Two', true",
            "'One, Two, Three', 'Two,', true",
            "'One, Two, Three', 'Two, ', true",
            "'One, Two, Three', '', true",
            "'', 'One', false",
            "'', '', true"
    })
    void testCheckingIsStringContainsWordWithoutNull(String text, String word, boolean expectedResult) {
        assertEquals(expectedResult, calculations.checkingIsStringContainsWord(text, word));
    }

    @Test
    @DisplayName("Task 5 with text = null")
    void testCheckingIsStringContainsWordWithNullText() {
        assertFalse(calculations.checkingIsStringContainsWord(null, "Word"));
    }

    @Test
    @DisplayName("Task 5 with word = null")
    void testCheckingIsStringContainsWordWithNullWord() {
        assertFalse(calculations.checkingIsStringContainsWord("Text", null));
    }

    @Test
    @DisplayName("Task 5 with word = null & text = null")
    void testCheckingIsStringContainsWordWithNullWordAndText() {
        assertFalse(calculations.checkingIsStringContainsWord(null, null));
    }
}
