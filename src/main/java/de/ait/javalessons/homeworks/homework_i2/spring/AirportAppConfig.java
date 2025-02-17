package de.ait.javalessons.homeworks.homework_i2.spring;

import de.ait.javalessons.homeworks.homework_i2.Destination;
import de.ait.javalessons.homeworks.homework_i2.Flight;
import de.ait.javalessons.homeworks.homework_i2.Office;
import de.ait.javalessons.homeworks.homework_i2.Plane;
import de.ait.javalessons.homeworks.homework_i2.TicketOffice;
import de.ait.javalessons.homeworks.homework_i2.WaitingRoom;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AirportAppConfig {

    @Bean
    public Office office() {
        return new Office();
    }

    @Bean
    public WaitingRoom waitingRoom() {
        return new WaitingRoom();
    }

    //Так как самолет в демонстрации только один пропишем его непосредственно здесь
    @Bean(name = "Boeing777")
    public Plane plane() {
        return new Plane("Boeing 777-200ER Dreamliner", 2, 6, 314);
    }

    //Для демонстрации используем только один рейс
    @Bean(name = "Boeing_to_New_York")
    public Flight flightToNewYork(Plane plane, Office office, WaitingRoom waitingRoom) {
        office.parseEmployees(); //временно здесь, пока не знаем как правильно конфигурировать
        waitingRoom.createPassengers();// также^
        return new Flight(plane, Destination.NEW_YORK.NEW_YORK, office, waitingRoom, "Pan American", "914");
    }

    @Bean
    public TicketOffice ticketOffice(Office office, WaitingRoom waitingRoom) {
        return new TicketOffice(office, waitingRoom);
    }
}