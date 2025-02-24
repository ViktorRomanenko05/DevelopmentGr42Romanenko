package de.ait.javalessons.homeworks.homework_6;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Getter
public class TVDataSource {

    private static final File file = Path.of("src","main", "java", "de", "ait", "javalessons", "homeworks", "homework_6", "tvresources", "programsCatalog.txt").toFile();
    private static final Logger LOGGER = LoggerFactory.getLogger(TVDataSource.class);

    private List<TVProgram> programsList = parsePrograms();


    //Method for parsing TV Programs from file to List
    private List<TVProgram> parsePrograms() {
        List<TVProgram> resultList = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] programData = line.split(",");
                if (programData.length == 5) {
                    String channel = programData[0].trim();
                    String programName = programData[1].trim();
                    int duration = Integer.parseInt(programData[2].trim());
                    boolean isLive = Boolean.parseBoolean(programData[3].trim());
                    double rating = Double.parseDouble(programData[4].trim());
                    TVProgram program = new TVProgram(channel, programName, duration, isLive, rating);
                    resultList.add(program);
                } else {
                    LOGGER.error("Invalid TVProgram data {}", line);
                }
            }
            LOGGER.info("TVPrograms parsed successfully");
        } catch (IOException exception) {
            LOGGER.error(exception.getMessage());
        }
        return resultList;
    }
}