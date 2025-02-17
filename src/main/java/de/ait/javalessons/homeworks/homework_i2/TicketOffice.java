package de.ait.javalessons.homeworks.homework_i2;

import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;

public class TicketOffice {

    private final Office office;
    private final WaitingRoom waitingRoom;
    Faker faker = new Faker();
    private static final Logger LOGGER = LoggerFactory.getLogger(TicketOffice.class);

    public TicketOffice(Office office, WaitingRoom waitingRoom) {
        this.office = office;
        this.waitingRoom = waitingRoom;
    }

    private static final HashMap <String, Ticket> tickets = new HashMap<>();
    private static final ArrayList<Employee> salesAgents = new ArrayList<>();

    //Метод продажи билета
    public void sellTicket(Passanger passanger) {
        Ticket ticket = new Ticket(randomDestination(), findSalesAgent(), passanger);
        ticket.setStatus(TicketStatus.CONFIRMED);
        passanger.getTickets().put(ticket.getTicketId(), ticket);
        tickets.put(ticket.getTicketId(), ticket);
        LOGGER.info(passanger.getName() + " " + passanger.getSurname() + " purchased the ticket to " + ticket.getDestination().getDescription() + " (Sales agent: " + ticket.getSalesAgent().getName() + " " + ticket.getSalesAgent().getSurname() + ")");
        LOGGER.info("Ticket #" + ticket.getTicketId() + ", status: " + ticket.getStatus().getDescription());
    }

    //Метод для произвольного выбора одного из направлений полёта
    private Destination randomDestination() {
        Destination[] destinations = {Destination.BERLIN, Destination.LONDON,
                Destination.MIAMI, Destination.PARIS, Destination.NEW_YORK};
        int rndNum = faker.random().nextInt(0, destinations.length - 1);
        return destinations[rndNum];
    }

    //Метод для произвольного выбора одного из сотрудников
    private Employee findSalesAgent() {
        getSalesAgents();
        int rndNum = faker.random().nextInt(0, salesAgents.size() - 1);
        Employee salesAgent = salesAgents.get(rndNum);
        return salesAgent;
    }

    //Метод для выбора сотрудников отдела продаж из списка персонала
    private void getSalesAgents() {
        for (Employee employee : office.getEmployees().values()) {
            if (employee.getRole() == Role.SALES_AGENT && employee.getStatus() != EmployeeStatus.BUSY) {
                office.getEmployees().get(employee.getId()).setStatus(EmployeeStatus.BUSY);
                LOGGER.info("Sales agent "+ employee.getName()+" "+ employee.getSurname() +" get status "+ employee.getStatus().getDescription());
                salesAgents.add(employee);
            }
        }
    }

    //Метод для продажи билетов всем пассажирам в WaitingRoom на произвольные направления
    public void sellTicketsForAll(){
        for (Passanger passanger : waitingRoom.getPassangers().values()) {
            sellTicket(passanger);
        }
    }

    //Метод для освобождения сотрудников TicketOffice
    public void freeSalesAgents(){
        for (Employee employee : salesAgents){
            if (employee.getStatus() == EmployeeStatus.BUSY){
                employee.setStatus(EmployeeStatus.FREE);
                LOGGER.info("Sales agent "+ employee.getName()+" "+ employee.getSurname() +" get status "+ employee.getStatus().getDescription());
            }
        }
    }
}
