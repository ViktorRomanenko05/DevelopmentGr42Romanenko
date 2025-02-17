package de.ait.javalessons.homeworks.homework_i2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

public class Employee extends Person {

    private static final Logger LOGGER = LoggerFactory.getLogger(Employee.class);

    private final Role role;
    private EmployeeStatus status;

    public Employee(String name, String surname, LocalDate dateOfBirth, Role role) {
        super(name, surname, dateOfBirth);
        this.role = role;
        this.status = EmployeeStatus.FREE;
    }

    public Role getRole() {
        return role;
    }

    public EmployeeStatus getStatus() {
        return status;
    }

    public void setStatus (EmployeeStatus status) {
        this.status = status;
    }

    @Override
    public void registrationOnFlight(){
        LOGGER.info(this.role.getDescription() + " " + this.getName() + " " + this.getSurname() + " registered");
    }

    @Override
    public String toString() {
        return "Employee{" +
                "role=" + role +
                ", status=" + status +
                "} " + super.toString();
    }
}
