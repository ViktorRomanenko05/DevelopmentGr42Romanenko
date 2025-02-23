package de.ait.javalessons.homeworks.homework_5;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoApp {

    private static final GeoDataProvider data = new GeoDataProvider();
    private static final Logger LOGGER = LoggerFactory.getLogger(GeoApp.class);

    public static void main(String[] args) {

        LOGGER.info("Program started");

        //Task 1
        //Using Stream API, filter the countries whose names start with the letter "C".
        line();
        System.out.println("Task 1");
        line();
        GeoDataProcessor.startsWithLetter(data.getCountries(), "C").forEach(System.out::println);
        line();

        //Task 2
        //Using Stream API, filter the cities whose names are longer than 6 characters.
        System.out.println("Task 2");
        line();
        GeoDataProcessor.filteringByNameLength(data.getCities(), Operations.GREATER, 6).forEach(System.out::println);
        line();

        //Task 3
        //Using Stream API, filter only the rivers whose names have an even number of letters.
        System.out.println("Task 3");
        line();
        GeoDataProcessor.filteringByEvenLetterCount(data.getRivers()).forEach(System.out::println);
        line();

        //Task 4
        //Using Stream API, filter the continents whose names are shorter than 7 characters.
        System.out.println("Task 4");
        line();
        GeoDataProcessor.filteringByNameLength(data.getContinents(), Operations.LESS, 7).forEach(System.out::println);
        line();

        //Task 5
        //Using Stream API, filter the countries whose names consist of 6 letters.
        System.out.println("Task 5");
        line();
        GeoDataProcessor.filteringByNameLength(data.getCountriesTask5(), Operations.EQUAL, 6).forEach(System.out::println);
        line();

        //Task 6
        //Using Stream API, filter the countries whose names contain the letter "a".
        System.out.println("Task 6");
        line();
        GeoDataProcessor.filteringNamesWithSpecifiedLetter(data.getCountriesTask5(), "a").forEach(System.out::println);
        line();

        //Task 7
        //Using Stream API, filter the cities whose names end with "o".
        System.out.println("Task 7");
        line();
        GeoDataProcessor.endsWithLetter(data.getCities(), "o").forEach(System.out::println);
        line();

        //Task 8
        //Using Stream API, filter the rivers whose names contain more than 7 letters.
        System.out.println("Task 8");
        line();
        GeoDataProcessor.filteringByNameLength(data.getRivers(), Operations.GREATER, 7).forEach(System.out::println);
        line();

        //Task 9
        //Using Stream API, filter the continents whose names start with "A".
        System.out.println("Task 9");
        line();
        GeoDataProcessor.startsWithLetter(data.getContinents(), "A").forEach(System.out::println);
        line();

        LOGGER.info("Program finished");
    }

    private static void line() {
        System.out.println("----------------------------------------------------------------");
    }
}
