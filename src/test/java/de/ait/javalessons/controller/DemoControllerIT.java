package de.ait.javalessons.controller;

import de.ait.javalessons.DevelopmentGr42RomanenkoApplication;
import de.ait.javalessons.configuration.SecurityConfigJDBC;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for the DemoController class.
 * This test class verifies the correctness and functionality of various DemoController endpoints,
 * using a TestRestTemplate to perform HTTP requests against the application running in a test environment.
 * The tests ensure that the endpoints return appropriate HTTP responses and content based on scenarios
 * that mimic both authenticated and unauthenticated access.
 *
 * Annotations and Test Configuration:
 * - @SpringBootTest: Boots up the entire Spring application context for testing.
 * - @ActiveProfiles: Specifies the "test" profile to use in this test class.
 * - @LocalServerPort: Injects the random port used by the running test server.
 *
 * This class also utilizes the following custom configurations:
 * - DevelopmentGr42Application: The main Spring Boot application class.
 * - SecurityConfigJDBC: A configuration class that sets up security rules and authentication for the application.
 *
 * Test Cases:
 * - testHomeAPI: Tests the home API endpoint for a successful response and specific content validation.
 * - testPublicInfoAPI: Tests a publicly accessible API endpoint without authentication.
 * - testDashboardPageAPIUnauthorized: Tests the unauthorized access to a user dashboard endpoint, verifying redirection or appropriate message.
 * - testDashboardPageAPIAuthorized: Tests the authorized access to an admin dashboard endpoint, verifying successful response and expected page content.
 */
@SpringBootTest(classes = {DevelopmentGr42RomanenkoApplication.class, SecurityConfigJDBC.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class DemoControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void  testHomeAPI(){
        String url = "http://localhost:" + port + "/";
        ResponseEntity<String> response = testRestTemplate.getForEntity(url, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("Главная страница");
    }

    @Test
    void  testPublicInfoAPI(){
        String url = "http://localhost:" + port + "/public/info";
        ResponseEntity<String> response = testRestTemplate.getForEntity(url, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("Страница доступна без авторизации");
    }

    @Test
    void testDashboardPageAPIUnauthorized(){
        String url = "http://localhost:" + port + "/user/dashboard";
        ResponseEntity<String> response = testRestTemplate.getForEntity(url, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().contains("Please sign in"));
    }

    @Test
    void testDashboardPageAPIAuthorized(){
        String url = "http://localhost:" + port + "/admin/dashboard";
        TestRestTemplate adminTemplate = testRestTemplate.withBasicAuth("admin", "adminpass");
        ResponseEntity<String> response = adminTemplate.getForEntity(url, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().contains("Админский раздел"));

    }
}
