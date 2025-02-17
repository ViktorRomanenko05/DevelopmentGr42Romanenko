package de.ait.javalessons.homeworks.homework_i2;

import com.github.javafaker.Faker;

import java.time.LocalDateTime;
import java.util.Objects;

public class Ticket {

    Faker faker = new Faker();

    private final String ticketId;
    private final LocalDateTime purchaseTime;
    private LocalDateTime expirationTime;
    private final Destination destination;
    private TicketStatus status;
    private final Employee salesAgent;
    private final Passanger passanger;

    public Ticket(Destination destination, Employee salesAgent, Passanger passanger) {
        this.ticketId = faker.idNumber().valid();
        this.purchaseTime = LocalDateTime.now();
        this.expirationTime = purchaseTime.plusDays(3);
        this.destination = destination;
        this.status = TicketStatus.BOOKED;
        this.salesAgent = salesAgent;
        this.passanger = passanger;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(ticketId, ticket.ticketId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ticketId);
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId='" + ticketId + '\'' +
                ", purchaseTime=" + purchaseTime +
                ", expirationTime=" + expirationTime +
                ", destination=" + destination +
                ", status=" + status +
                ", salesAgent=" + salesAgent +
                ", passanger=" + passanger +
                '}';
    }

    public String getTicketId() {
        return ticketId;
    }

    public LocalDateTime getPurchaseTime() {
        return purchaseTime;
    }

    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }

    public Destination getDestination() {
        return destination;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public Employee getSalesAgent() {
        return salesAgent;
    }

    public Passanger getPassanger() {
        return passanger;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }
}
