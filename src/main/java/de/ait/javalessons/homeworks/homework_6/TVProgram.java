package de.ait.javalessons.homeworks.homework_6;

import java.util.Objects;

public class TVProgram {

    private String channel;      // Название канала
    private String programName;  // Название передачи
    private int duration;        // Длительность передачи (в минутах)
    private boolean isLive;      // Признак прямого эфира
    private double rating;       // Рейтинг передачи (например, от 0.0 до 10.0)

    public TVProgram(String channel, String programName, int duration, boolean isLive, double rating) {
        this.channel = channel;
        this.programName = programName;
        this.duration = duration;
        this.isLive = isLive;
        this.rating = rating;
    }

    public String getChannel() {
        return channel;
    }

    public String getProgramName() {
        return programName;
    }

    public int getDuration() {
        return duration;
    }

    public boolean isLive() {
        return isLive;
    }

    public double getRating() {
        return rating;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TVProgram tvProgram = (TVProgram) o;
        return Objects.equals(programName, tvProgram.programName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(programName);
    }

    @Override
    public String toString() {
        return String.format(
                "TVProgram{programName='%s', channel='%s', duration=%d, isLive=%b, rating=%.1f}",
                programName, channel, duration, isLive, rating
        );
    }
}
