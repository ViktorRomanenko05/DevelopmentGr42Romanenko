package de.ait.javalessons.homeworks.homework_i2;

import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Office {
    private static final File file = Path.of("src", "main", "java", "de", "ait", "javalessons", "homeworks", "homework_i2", "employees.txt").toFile();
    private static final HashMap<String, Employee> employees = new HashMap<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(Office.class);
    private static final Faker faker = new Faker();

    //Метод для парсинга данных из файла и добавления полученных объектов в HashMap
    public void parseEmployees() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] employeeData = line.split(",");
                if (employeeData.length == 4) {
                    String name = employeeData[0].trim();
                    String surname = employeeData[1].trim();
                    LocalDate birthDate = LocalDate.parse(employeeData[2].trim());
                    Role role = Role.fromStringRole(employeeData[3].trim());
                    Employee employee = new Employee(name, surname, birthDate, role);
                    employees.put(employee.getId(), employee);
                } else {
                    LOGGER.error("Invalid employee data {}", line);
                }
            }
            LOGGER.info("Employees parsed successfully");
        } catch (IOException exception) {
            LOGGER.error(exception.getMessage());
        }
    }

    //Метод возвращает произвольного стюарта со статусом FREE
    public Employee findStewart() {
        ArrayList<Employee> stewarts = new ArrayList<>();
        for (Employee employee : employees.values()) {
            if (employee.getRole() == Role.STEWART && employee.getStatus() != EmployeeStatus.BUSY) {
                stewarts.add(employee);
            }
        }
        if (stewarts.isEmpty()) {
            LOGGER.info("Stewarts not found");
            return null;
        }
        int rndNum = faker.random().nextInt(0, stewarts.size() - 1);
        Employee stewart = stewarts.get(rndNum);
        employees.get(stewart.getId()).setStatus(EmployeeStatus.BUSY);
        stewart.registrationOnFlight();
        return stewart;
    }

    //метод возвращает произвольного пилота со статусом FREE
    public Employee findPilots() {
        ArrayList<Employee> pilots = new ArrayList<>();
        for (Employee employee : employees.values()) {
            if (employee.getRole() == Role.PILOT && employee.getStatus() != EmployeeStatus.BUSY) {
                pilots.add(employee);
            }
        }
        if (pilots.isEmpty()) {
            LOGGER.info("Pilots not found");
            return null;
        }
        int rndNum = faker.random().nextInt(0, pilots.size() - 1);
        Employee pilot = pilots.get(rndNum);
        employees.get(pilot.getId()).setStatus(EmployeeStatus.BUSY);
        pilot.registrationOnFlight();
        return pilot;
    }

    public Map <String, Employee> getEmployees() {
        return employees;
    }
}
