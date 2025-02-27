package de.ait.javalessons.homeworks.homework_7;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Calculations {

    private static final Logger LOGGER = LoggerFactory.getLogger(Calculations.class);

    //Task 1
    //Testing the Addition Method
    //Write a parameterized test for the add(int a, int b) method,
    //which takes two integers and returns their sum.

    //1. Create a class with the method add(int a, int b).

    public int addition(int a, int b) {
        return a + b;
    }

    //Task 2
    //Checking if a Number is Even
    //Write a parameterized test for the isEven(int number) method,
    //which checks if a number is even.

    //1. Create a class with the method isEven(int number), which returns
    // true if the number is even and false otherwise.

    public boolean isEven(int number) {
        return number % 2 == 0;
    }

    //Task 3
    //Testing Division Method Correctness
    //Write a parameterized test for the divide(int a, int b) method,
    //which performs division of two numbers and returns the result.

    //1. Create a class with the method divide(int a, int b), which returns
    //the result of a / b.

    public int divide(int a, int b) {
        if (b == 0) {
            LOGGER.warn("Division by 0");
            return 0;
        }
        return (a / b);
    }

    //Task 4
    //Checking String Length
    //Write a parameterized test for the getLength(String str) method,
    //which takes a string and returns its length.

    //1. Create a class with the method getLength(String str).

    public int getLength(String str) {
        if (str == null) {
            LOGGER.error("String is null");
            return -1;
        }
        return str.length();
    }

    //Task 5
    //Checking if a String Contains a Specific Word
    //Write a parameterized test for the containsWord(String text, String word)
    //method, which checks if a string contains a given word.

    //1. Create a class with the method containsWord(String text, String word),
    //which returns true if text contains word.

    public boolean checkingIsStringContainsWord(String text, String word) {
        if (text == null || word == null) {
            LOGGER.error("Argument is null");
            return false;
        } else {
            if (text.isEmpty()) {
                LOGGER.warn("Argument 'text' is empty");
            }
            if (word.isEmpty()) {
                LOGGER.warn("Argument 'word' is empty");
            }
            return text.contains(word);
        }
    }
}
