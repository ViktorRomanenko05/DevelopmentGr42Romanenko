package de.ait.javalessons.homeworks.homework_6;

public class TVProgramApp {

    private static TVDataSource data = new TVDataSource();

    public static void main(String[] args) {
        //Task 1
        //Find all programs with a rating above a specified threshold (e.g., > 8.0).
        line();
        System.out.println("Task 1");
        line();
        TVProgramsProcessor.filterByRating(data.getProgramsList(), 8).forEach(System.out::println);
        line();

        //Task 2
        //Transform TVProgram objects into user-friendly strings.
        System.out.println("Task 2");
        line();
        TVProgramsProcessor.turnToStrings(data.getProgramsList()).forEach(System.out::println);
        line();

        //Task 3
        //Check if there is at least one program that is broadcast live (isLive == true)
        System.out.println("Task 3");
        System.out.println(TVProgramsProcessor.checkIsLiveContains(data.getProgramsList()));
        line();

        //Task 4
        //Determine the longest program (maximum value of the duration field)
        System.out.println("Task 4");
        System.out.println(TVProgramsProcessor.maxDurationDetector(data.getProgramsList()));
        line();

        //Task 5
        //Calculating the Average Rating
        System.out.println("Task 5");
        System.out.println(TVProgramsProcessor.calculatingTheAverageRating(data.getProgramsList()));
        line();

        //Task 6
        //Grouping by Channel
        System.out.println("Task 6");
        TVProgramsProcessor.groupingByChannel(data.getProgramsList()).forEach((s, programs) -> System.out.println(s + programs.toString()));
        line();

        //Task 7.1
        //Sort the list of programs by channel (e.g., in descending order)
        System.out.println("Task 7.1");
        TVProgramsProcessor.sortingByChannelName(data.getProgramsList(), true).forEach(System.out::println);
        line();

        //Task 7.2
        //Sort the list of programs by rating (e.g., in descending order)
        System.out.println("Task 7.2");
        TVProgramsProcessor.sortingByRating(data.getProgramsList(), true).forEach(System.out::println);
        line();
    }

    //Print line
    private static void line (){
        System.out.println("-------------------------------------------------------");
    }


}
