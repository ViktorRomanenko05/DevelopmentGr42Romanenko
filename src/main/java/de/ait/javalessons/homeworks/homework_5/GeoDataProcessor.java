package de.ait.javalessons.homeworks.homework_5;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class GeoDataProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(GeoDataProcessor.class);
    private static final String NULL_WARN_MESSAGE = "Input parameters is incorrect";

    private GeoDataProcessor() {
    }

    //Method for filtering names by first letter
    public static List<String> startsWithLetter(List<String> names, String letter, boolean reverse) {
        if (names != null && letter != null) {
            List<String> resultList = names.stream()
                    .filter(Objects::nonNull)
                    .filter(name -> !name.isEmpty())
                    .filter(name ->(reverse ? name.endsWith(letter) : name.startsWith(letter)))
                    .toList();
            LOGGER.info("{} names with {} letter {} was found", resultList.size(), reverse ? "last":"first", letter);
            return resultList;
        } else {
            LOGGER.warn(NULL_WARN_MESSAGE);
        }
        return Collections.emptyList();
    }

    //Method for filtering names by last letter
//    public static List<String> endsWithLetter(List<String> names, String letter) {
//        if (names != null && letter != null) {
//            List<String> resultList = names.stream()
//                    .filter(Objects::nonNull)
//                    .filter(name -> !name.isEmpty())
//                    .filter(name -> name.endsWith(letter))
//                    .toList();
//            LOGGER.info("{} names with last letter {} was found", resultList.size(), letter);
//            return resultList;
//        } else {
//            LOGGER.warn(NULL_WARN_MESSAGE);
//        }
//        return Collections.emptyList();
//    }

    //Method for filtering names by name length
    public static List<String> filteringByNameLength(List<String> names, Operations operation, int nameLength) {
        if (names != null && nameLength > 0) {
            List<String> resultList = names.stream()
                    .filter(Objects::nonNull)
                    .filter(name -> !name.isEmpty())
                    .filter(name -> operation.apply(name.length(), nameLength))
                    .toList();
            LOGGER.info("{} names was found", resultList.size());
            return resultList;
        } else {
            LOGGER.warn(NULL_WARN_MESSAGE);
        }
        return Collections.emptyList();
    }

    //Method for filtering names by even letter Count
    public static List<String> filteringByEvenLetterCount(List<String> names) {
        if (names != null) {
            List<String> resultList = names.stream()
                    .filter(Objects::nonNull)
                    .filter(name -> !name.isEmpty())
                    .filter(name -> name.length() % 2 == 0)
                    .toList();
            LOGGER.info("{} names with even count of letters was found", resultList.size());
            return resultList;
        } else {
            LOGGER.warn(NULL_WARN_MESSAGE);
        }
        return Collections.emptyList();
    }

    //Method for filtering names containing a specified letter
    public static List<String> filteringNamesWithSpecifiedLetter(List<String> names, String letter) {
        if (names != null && letter != null) {
            List<String> resultList = names.stream()
                    .filter(Objects::nonNull)
                    .filter(name -> !name.isEmpty())
                    .filter(name -> name.contains(letter))
                    .toList();
            LOGGER.info("{} names containing the letter {} was found", resultList.size(), letter);
            return resultList;
        } else {
            LOGGER.warn(NULL_WARN_MESSAGE);
        }
        return Collections.emptyList();
    }
}
