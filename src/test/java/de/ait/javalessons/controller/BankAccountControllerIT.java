package de.ait.javalessons.controller;

import de.ait.javalessons.exceptions.InvalidAmountException;
import de.ait.javalessons.model.BankAccount;
import de.ait.javalessons.repositories.BankAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BankAccountControllerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    private static final String BASE_URL = "/accounts";

    BankAccount accountOne;
    BankAccount accountTwo;
    BankAccount accountThree;
    BankAccount accountFour;
    BankAccount accountFive;


    @BeforeEach
    void setUp() {
        accountOne = new BankAccount("DE11111111", "Friedrich Müller", 0.0);
        accountTwo = new BankAccount("DE22222222", "Johann Schmidt", 1000);
        accountThree = new BankAccount("DE33333333", "Heinrich Weber", 5500.55);
        accountFour = new BankAccount("DE44444444", "Klaus Schneider", 10000.00);
        accountFive = new BankAccount("DE55555555", "Wolfgang Fischer", 7500);
        bankAccountRepository.deleteAll();
        bankAccountRepository.saveAll(
                List.of(
                        accountOne,
                        accountTwo,
                        accountThree,
                        accountFour,
                        accountFive
                ));
    }

    @Test
    @DisplayName("Get all accounts test")
    void testGetAccountsReturnAllAccounts() {
        ResponseEntity<BankAccount[]> response = testRestTemplate.getForEntity(BASE_URL, BankAccount[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(5, response.getBody().length);
        assertEquals("DE44444444", response.getBody()[3].getAccountNumber());
    }

    @Test
    @DisplayName("Get bank account by id")
    void testGetAccountByIdReturnBankAccount() {
        String url = BASE_URL + "/" + accountThree.getId();
        ResponseEntity<BankAccount> response = testRestTemplate.getForEntity(url, BankAccount.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("DE33333333", response.getBody().getAccountNumber());
    }

    @Test
    @DisplayName("Get bank account by id negative")
    void testGetAccountByIdNegative() {
        String url = BASE_URL + "/" + 888;
        ResponseEntity<BankAccount> response = testRestTemplate.getForEntity(url, BankAccount.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("Create bank account test")
    void testCreateBankAccount (){
        String url = BASE_URL + "?accountNumber=DE66666666&ownerName=Alex Lewis";
        ResponseEntity<BankAccount> response = testRestTemplate.postForEntity(url,null ,BankAccount.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Alex Lewis", response.getBody().getOwnerName());
        BankAccount[]accounts = testRestTemplate.getForEntity(BASE_URL, BankAccount[].class).getBody();
        assertEquals(6, accounts.length);
        assertTrue(Arrays.stream(accounts).anyMatch(account -> account.getAccountNumber().equals("DE66666666")));
    }

    @Test
    @DisplayName("Deposit positive test")
    void testDepositPositive (){
        String url = BASE_URL + "/" + accountFour.getId() + "/deposit?amount=500.55";
        ResponseEntity <Double> response = testRestTemplate.postForEntity(url,null, Double.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(10500.55, response.getBody());
        BankAccount accountFourRenew = testRestTemplate.getForEntity(BASE_URL + "/" + accountFour.getId(), BankAccount.class).getBody();
        assertEquals(10500.55, accountFourRenew.getBalance());
    }

    @Test
    @DisplayName("Deposit negative test - amount exceeds max limit")
    void testDepositNegative() {
        String url = BASE_URL + "/" + accountFour.getId() + "/deposit?amount=5001";
        ResponseEntity<String> response = testRestTemplate.postForEntity(url, null, String.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    @DisplayName("Withdraw positive test")
    void testWithdrawPositive () {
        String url = BASE_URL + "/" + accountFive.getId() + "/withdraw?amount=2000";
        ResponseEntity <Double> response = testRestTemplate.postForEntity(url,null, Double.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(5500.00, response.getBody());
        BankAccount accountFiveRenew = testRestTemplate.getForEntity(BASE_URL + "/" + accountFive.getId(), BankAccount.class).getBody();
        assertEquals(5500.00, accountFiveRenew.getBalance());
    }

    @Test
    @DisplayName("Withdraw negative test - amount exceeds max limit")
    void testWithdrawNegative() {
        String url = BASE_URL + "/" + accountFour.getId() + "/withdraw?amount=2001";
        ResponseEntity<String> response = testRestTemplate.postForEntity(url, null, String.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    @DisplayName("Withdraw negative test - amount on account lower min limit")
    void testWithdrawNegativeLowAmount() {
        String url = BASE_URL + "/" + accountTwo.getId() + "/withdraw?amount=990";
        ResponseEntity<String> response = testRestTemplate.postForEntity(url, null, String.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}