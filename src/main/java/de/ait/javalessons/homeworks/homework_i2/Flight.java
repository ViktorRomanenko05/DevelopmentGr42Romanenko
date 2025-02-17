package de.ait.javalessons.homeworks.homework_i2;

import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Flight {

    Office office;
    WaitingRoom waitingRoom;
    private static final Logger LOGGER = LoggerFactory.getLogger(Flight.class);
    Faker faker = new Faker();

    private Plane plane;
    private String flightNumber;
    private String company;
    private Destination destinationPoint;
    private LocalDateTime departureTime;
    private LocalDateTime arriveTime;
    private ArrayList<Employee> crew;
    private ArrayList<Passanger> passangers;
    private boolean flightIsOk = false;

    public Flight(Plane plane, Destination destinationPoint, Office office, WaitingRoom waitingRoom, String company, String flightNumber) {
        this.plane = plane;
        this.company = company;
        this.flightNumber = flightNumber;
        this.destinationPoint = destinationPoint;
        this.departureTime = LocalDateTime.now().plusHours(1);
        this.arriveTime = LocalDateTime.now().plusHours(10);
        this.crew = new ArrayList<>();
        this.passangers = new ArrayList<>();
        this.office = office;
        this.waitingRoom = waitingRoom;
    }

    //Метод регистрации на рейс
    public boolean boarding() {
        for (int i = 0; i < plane.getPilotsQuantity(); i++) {
            Employee pilot = office.findPilots();
            if (pilot != null) {
                crew.add(pilot);
            } else break;
        }
        if (checkPilotsQuantity() < plane.getPilotsQuantity()) {
            LOGGER.info("Pilots is missing for departure");
            return false;
        } else {
            LOGGER.info("Pilots on board");
        }
        for (int i = 0; i < plane.getStewartsQuantity(); i++) {
            Employee stewart = office.findStewart();
            if (stewart != null) {
                crew.add(stewart);
            } else break;
        }
        if (checkStewartsQuantity() < plane.getStewartsQuantity()) {
            LOGGER.info("Stewarts is missing for departure");
            return false;
        } else {
            LOGGER.info("Stewarts on board");
        }
        for (int i = 0; i < plane.getPassangersQuantity(); i++) {
            Passanger passanger = waitingRoom.findPassengers(destinationPoint);
            if (passanger != null) {
                passangers.add(passanger);
            } else break;
        }
        if (passangers.isEmpty()) {
            LOGGER.info("Passangers is missing");
            return false;
        } else {
            line();
            LOGGER.info(passangers.size() + " Passangers on board");
        }
        return true;
    }

    //Метод начала полета
    public void startFlight() {
        if (!boarding()) {
            line();
            LOGGER.info("Takeoff is impossible");
        } else {
            line();
            System.out.println("Flight #" + flightNumber);
            System.out.println("Company: " + company);
            plane.takeoff();
            System.out.println("Departure time: " + DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm").format(departureTime));
            flightIsOk = true;
        }
    }

    //Метод завершения полета
    public void finishFlight() {
        if (flightIsOk) {
            plane.landing();
            passangers.clear();
            System.out.println("Passengers got out");
            for (Employee employee : crew) {
                employee.setStatus(EmployeeStatus.FREE);
                LOGGER.info(employee.getRole().getDescription() + " " + employee.getName() + " " + employee.getSurname() + " get status: " + employee.getStatus().getDescription());
            }
            crew.clear();
            System.out.println("Crew got out");
            System.out.println("Arrive time: " + DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm").format(arriveTime));
        }
    }

    private int checkPilotsQuantity() {
        long pilotsQuantity = crew.stream().filter(employee -> employee.getRole() == Role.PILOT).count();
        return (int) pilotsQuantity;
    }

    private int checkStewartsQuantity() {
        long stewartsQuantity = crew.stream().filter(employee -> employee.getRole() == Role.STEWART).count();
        return (int) stewartsQuantity;
    }

    public Plane getPlane() {
        return plane;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getCompany() {
        return company;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public Destination getDestination() {
        return destinationPoint;
    }

    public LocalDateTime getArriveTime() {
        return arriveTime;
    }

    public ArrayList<Employee> getCrew() {
        return crew;
    }

    public ArrayList<Passanger> getPassangers() {
        return passangers;
    }

    private static void line() {
        System.out.println("---------------------------------------------------------------------------------------");
    }

    @Override
    public String toString() {
        return "Flight{" +
                "office=" + office +
                ", waitingRoom=" + waitingRoom +
                ", plane=" + plane +
                ", flightNumber='" + flightNumber + '\'' +
                ", company='" + company + '\'' +
                ", destinationPoint=" + destinationPoint +
                ", departureTime=" + departureTime +
                ", arriveTime=" + arriveTime +
                ", crew=" + crew +
                ", passangers=" + passangers +
                ", flightIsOk=" + flightIsOk +
                '}';
    }
}
