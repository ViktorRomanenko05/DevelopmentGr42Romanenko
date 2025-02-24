package de.ait.javalessons.homeworks.homework_6;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TVProgramsProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(TVProgramsProcessor.class);
    private static final String PARAMETERS_WARN_MESSAGE = "Input parameters is incorrect";

    //Method for filtering TV Programs by rating
    public static List<TVProgram> filterByRating (List<TVProgram> programs, double rating){
        List<TVProgram> resultList = new ArrayList<>();
        if (programs != null && rating >= 0 && rating <= 10) {
            resultList = programs.stream()
                    .filter(tvProgram -> tvProgram.getRating() > rating)
                    .toList();
            LOGGER.info("{} programs was found", resultList.size());
        }
        else {
            LOGGER.warn(PARAMETERS_WARN_MESSAGE);
        }
        if(resultList.isEmpty()){
            LOGGER.info("List of TVPrograms is empty");
        }
        return resultList;
    }


    //Method for turning objects to strings
    public static List<String> turnToStrings (List<TVProgram> programs){
        List<String> resultList = new ArrayList<>();
        if (programs != null){
            resultList = programs.stream()
                    .sorted(Comparator.comparing(TVProgram::getChannel))
                    .map(program -> String.format(
                    "Channel: '%s' | ProgramName: '%s' | Rating: %.1f",
                    program.getChannel(), program.getProgramName(), program.getRating()
            ))
                    .toList();
        }else {
            LOGGER.warn(PARAMETERS_WARN_MESSAGE);
        }
        if(resultList.isEmpty()){
            LOGGER.info("TV programs not found");
        }
        return resultList;
    }

    //Method for checking live programs
    public static boolean checkIsLiveContains (List<TVProgram> programs){
        boolean result = false;
        if (programs !=null){
            result = programs.stream()
                    .anyMatch(TVProgram::isLive);
        }
        else LOGGER.warn(PARAMETERS_WARN_MESSAGE);
        if (result) {
            LOGGER.info("Live program is contains");
        } else {
            LOGGER.info("Live program is not contains");
        }
        return result;
    }

    //Method for longest program detection
    public static TVProgram maxDurationDetector (List<TVProgram> programs) {
        if (programs != null && !programs.isEmpty()){
            return programs.stream()
                    .max(Comparator.comparingInt(TVProgram::getDuration))
                    .orElse(null);
        }
        else {
            LOGGER.warn(PARAMETERS_WARN_MESSAGE);
            return null;
        }
    }

    //Method for calculating the average rating
    public static double calculatingTheAverageRating (List<TVProgram> programs) {
        double result = 0.0;
        if (programs !=null && !programs.isEmpty()) {
            double average = programs.stream()
                    .mapToDouble(TVProgram::getRating)
                    .average()
                    .orElse(0);
            result = (double) Math.round(average*10)/10;
        }
        else {
            LOGGER.warn(PARAMETERS_WARN_MESSAGE);
        }
        return result;
    }

    //Method for grouping by channel to map
    public static Map <String, List<TVProgram>> groupingByChannel(List<TVProgram> programs){
        Map<String, List<TVProgram>> result = new HashMap<>();
        if(programs != null) {
            result = programs.stream()
                    .collect(Collectors.groupingBy(TVProgram::getChannel));
        }
        else {
            LOGGER.warn(PARAMETERS_WARN_MESSAGE);
        }
        return result;
    }

    //Method for sorting TVPrograms by channel name
    public static List <TVProgram> sortingByChannelName(List<TVProgram> programs, boolean reverse){
        List<TVProgram> result = new ArrayList<>();
        if(programs != null && !programs.isEmpty()) {
            result = programs.stream()
                    .sorted(reverse ? Comparator.comparing(TVProgram::getChannel).reversed()
                            : Comparator.comparing(TVProgram::getChannel))
                    .toList();
        }
        else {
            LOGGER.warn(PARAMETERS_WARN_MESSAGE);
        }
        return result;
    }

    //Method for sorting TVPrograms by rating
    public static List <TVProgram> sortingByRating(List<TVProgram> programs, boolean reverse){
        List<TVProgram> result = new ArrayList<>();
        if(programs != null && !programs.isEmpty()) {
            result = programs.stream()
                    .sorted(reverse ? Comparator.comparing(TVProgram::getRating).reversed()
                            : Comparator.comparing(TVProgram::getRating))
                    .toList();
        }
        else {
            LOGGER.warn(PARAMETERS_WARN_MESSAGE);
        }
        return result;
    }
}
