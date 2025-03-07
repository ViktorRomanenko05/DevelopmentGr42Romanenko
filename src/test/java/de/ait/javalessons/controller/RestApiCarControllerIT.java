package de.ait.javalessons.controller;

import de.ait.javalessons.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RestApiCarControllerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    private static final String BASE_URL = "/cars";

    @BeforeEach
    void setUp() {

    }

    @Test
    @Order(1)
    void testGetCarsReturnDefaultCars() {
        ResponseEntity<Car[]> response = testRestTemplate.getForEntity(BASE_URL, Car[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(4, response.getBody().length);
        assertEquals("BMW M1", response.getBody()[0].getName());

    }

    @Test
    @Order(2)
    void testGetCarByIdWasFound() {
        ResponseEntity<Car> response = testRestTemplate.getForEntity(BASE_URL + "/1", Car.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("BMW M1", response.getBody().getName());
        assertEquals("1", response.getBody().getId());
    }

    @Test
    @Order(3)
    void testGetCarByIdWasNotFound() {
        ResponseEntity<Car> response = testRestTemplate.getForEntity(BASE_URL + "/10", Car.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    @Order(4)
    void testPostCarAddNewCar() {
        Car carToAdd = new Car("5", "Tesla Model S");
        ResponseEntity<Car> response = testRestTemplate.postForEntity(BASE_URL, carToAdd, Car.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Tesla Model S", response.getBody().getName());
        assertEquals("5", response.getBody().getId());
    }

    @Test
    @Order(5)
    void testPutCarUpdateCarInfo() {
        Car carToUpdate = new Car("1", "Tesla Model S");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Car> request = new HttpEntity<>(carToUpdate, headers);
        ResponseEntity<Car> response = testRestTemplate.exchange(BASE_URL + "/1", HttpMethod.PUT, request, Car.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Tesla Model S", response.getBody().getName());
    }

}