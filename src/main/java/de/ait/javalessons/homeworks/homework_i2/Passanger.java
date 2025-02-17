package de.ait.javalessons.homeworks.homework_i2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class Passanger extends Person {

    private static final Logger LOGGER = LoggerFactory.getLogger(Passanger.class);

    private HashMap<String, Ticket> tickets = new HashMap<>();

    public HashMap<String, Ticket> getTickets() {
        return tickets;
    }

    @Override
    public void registrationOnFlight() {
        LOGGER.info("Passanger " + this.getName() + " " + this.getSurname() + " was registered");
    }

    @Override
    public String toString() {
        return "Passanger{" +
                "tickets=" + tickets +
                "} " + super.toString();
    }
}
