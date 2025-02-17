package de.ait.javalessons.homeworks.homework_i2;

import lombok.Getter;

@Getter
public enum Destination {
    PARIS("Paris"),
    LONDON("London"),
    BERLIN("Berlin"),
    NEW_YORK("New York"),
    MIAMI("Miami");

    private final String description;

    Destination(String description) {
        this.description = description;
    }

}
