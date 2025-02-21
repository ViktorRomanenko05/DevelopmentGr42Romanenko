package de.ait.javalessons.homeworks.homework_5;

import lombok.Getter;

import java.io.File;
import java.nio.file.Path;

public enum FileManager {
    CITIES("cities.txt"),
    CONTINENTS("continents.txt"),
    COUNTRIES("countries.txt"),
    COUNTRIES_TASK_5("countriesTask5.txt"),
    RIVERS("rivers.txt");

    @Getter
    private final File file;

    private final String DIRECTORY_PATH = "src/main/java/de/ait/javalessons/homeworks/homework_5/geoAppResources";

    FileManager(String fileName) {
        this.file = Path.of(DIRECTORY_PATH, fileName).toFile();
    }
}
