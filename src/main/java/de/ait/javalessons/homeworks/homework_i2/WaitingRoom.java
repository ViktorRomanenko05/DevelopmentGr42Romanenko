package de.ait.javalessons.homeworks.homework_i2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class WaitingRoom {
    private static final HashMap <String, Passanger> passengers = new HashMap<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(WaitingRoom.class);

    //метод генерации данных пассажиров в зале ожидания
    public void createPassengers() {
        for (int i = 0; i < 500; i++) {
            Passanger passanger = new Passanger();
            passengers.put(passanger.getId(), passanger);
            //if (passengers.isEmpty()) {
                //LOGGER.error("Passengers was not added");
                //return;
            //}
        }

        LOGGER.info("Waiting room is full");
    }

    //Метод возвращает произвольного пассажира с билетом на необходимое направление
    public Passanger findPassengers(Destination destinationPoint) {
        Iterator<Passanger> iterator = passengers.values().iterator();

        while (iterator.hasNext()) {
            Passanger passanger = iterator.next();
            for (Ticket ticket : passanger.getTickets().values()) {
                if (ticket.getDestination() == destinationPoint && ticket.getStatus() == TicketStatus.CONFIRMED) {
                    ticket.setStatus(TicketStatus.USED);
                    iterator.remove();
                    passanger.registrationOnFlight();
                    LOGGER.info("Ticket # " + ticket.getTicketId() + " to " + ticket.getDestination().getDescription() + ", get status: " + ticket.getStatus().getDescription());
                    return passanger;
                }
            }
        }
        return null;
    }

    public Map<String, Passanger> getPassangers() {
        return passengers;
    }
}
