package de.ait.javalessons.homeworks.homework_i2;

import lombok.Getter;

@Getter
public enum TicketStatus {
    BOOKED("booked"),
    CONFIRMED("confirmed"),
    CANCELED("cancelled"),
    USED("used");

    private final String description;

    TicketStatus(String description) {
        this.description = description;
    }
}
