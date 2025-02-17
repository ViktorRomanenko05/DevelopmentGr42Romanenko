package de.ait.javalessons.homeworks.homework_i2.spring;

import de.ait.javalessons.homeworks.homework_i2.Flight;
import de.ait.javalessons.homeworks.homework_i2.Plane;
import de.ait.javalessons.homeworks.homework_i2.TicketOffice;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringAirportClient {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AirportAppConfig.class);
        TicketOffice ticketOffice = context.getBean(TicketOffice.class);
        Plane boeing777 = context.getBean("Boeing777", Plane.class);
        Flight flightToNewYork = context.getBean(Flight.class);

        //продаем билеты всем пассажирам на произвольные рейсы
        ticketOffice.sellTicketsForAll();

        //Освобождаем кассиров
        ticketOffice.freeSalesAgents();

        //Отображаем данные о самолете
        line();
        System.out.println("Plane: " + boeing777.getModelOfPlane() + " - " + boeing777.getPassangersQuantity() + " passenger places");

        line();
        flightToNewYork.startFlight();
        line();
        flightToNewYork.finishFlight();
        line();

    }

    private static void line() {
        System.out.println("---------------------------------------------------------------------------------------");
    }
}
