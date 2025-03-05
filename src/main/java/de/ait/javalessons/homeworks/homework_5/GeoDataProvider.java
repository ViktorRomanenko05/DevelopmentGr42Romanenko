package de.ait.javalessons.homeworks.homework_5;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter
public class GeoDataProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(GeoDataProvider.class);

    private final List<String> cities = readGeoData(FileManager.CITIES.getFile());
    private final List<String> continents = readGeoData(FileManager.CONTINENTS.getFile());
    private final List<String> countries = readGeoData(FileManager.COUNTRIES.getFile());
    private final List<String> countriesTask5 = readGeoData(FileManager.COUNTRIES_TASK_5.getFile());
    private final List<String> rivers = readGeoData(FileManager.RIVERS.getFile());

    //Method for reading data from file
    private static List<String> readGeoData(File file) {
        List<String> resultList = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    resultList.add(line.trim());
                } else {
                    LOGGER.warn("Empty string");
                }
            }
        } catch (IOException exception) {
            LOGGER.error(exception.getMessage());
        }
        return resultList;
    }
}
