package de.ait.javalessons.service;

import de.ait.javalessons.exceptions.AccountNotFoundException;
import de.ait.javalessons.exceptions.AccountNumberException;
import de.ait.javalessons.exceptions.InvalidAmountException;
import de.ait.javalessons.model.BankAccount;
import de.ait.javalessons.repositories.BankAccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Slf4j
public class BankAccountServiceTest {

    @Autowired
    BankAccountRepository bankAccountRepository;
    @Autowired
    BankAccountService bankAccountService;

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

    //Get all bank accounts tests

    @Test
    @DisplayName("Get all bank accounts")
    void getAllBankAccountsTest() {
        assertEquals(5, bankAccountService.getAllBankAccounts().size());
        assertTrue(bankAccountRepository.existsBankAccountByAccountNumber("DE44444444"));
    }

    //Find bank account by id tests

    @Test
    @DisplayName("Find bank account by id positive")
    void testFindBankAccountByIdPositive() {
        Optional<BankAccount> foundAccount = bankAccountService.findBankAccountById(accountTwo.getId());
        assertEquals(accountTwo.getId(), foundAccount.get().getId());
        assertEquals("DE22222222", foundAccount.get().getAccountNumber());
    }

    @Test
    @DisplayName("Find bank account by id negative")
    void testFindBankAccountByIdNegative() {
        Long maxId = bankAccountRepository.findAll().stream()
                .mapToLong(BankAccount::getId)
                .max()
                .orElse(0L);
        Long testId = maxId + 1;
        Optional<BankAccount> foundAccount = bankAccountService.findBankAccountById(testId);
        assertTrue(foundAccount.isEmpty());
    }

    @Test
    @DisplayName("Find bank account with null id")
    void testFindBankAccountByIdWhenIdIsNull() {
        assertThrows(IllegalArgumentException.class, ()->bankAccountService.findBankAccountById(null));
    }

    //Save new bank account by id tests

    @Test
    @DisplayName("Save New Bank Account Positive")
    void TestSaveNewBankAccountPositive() {
        BankAccount accountForAdd = new BankAccount("DE66666666", "Andreas Dold", 8000);
        bankAccountService.saveNewBankAccount(accountForAdd);
        assertEquals(6, bankAccountRepository.count());
        assertTrue(bankAccountRepository.existsBankAccountByAccountNumber("DE66666666"));
    }

    @Test
    @DisplayName("Save New Bank Account Negative")
    void TestSaveNewBankAccountNegative() {
        BankAccount accountForAdd = new BankAccount("DE33333333", "Mario Trüby", 8000);
        assertThrows(AccountNumberException.class, () -> bankAccountService.saveNewBankAccount(accountForAdd));
    }

    @Test
    @DisplayName("Save new bank account with null argument")
    void TestSaveNewBankAccountWithNull() {
        assertThrows(IllegalArgumentException.class, () -> bankAccountService.saveNewBankAccount(null));
    }

    //Deposit tests

    @ParameterizedTest
    @CsvFileSource(resources = "/deposit.csv", numLinesToSkip = 1)
    @DisplayName("Deposit Positive")
    void TestDepositPositive(double amount, double expectedBalance) {
        bankAccountService.deposit(amount, accountThree.getId());
        assertEquals(expectedBalance, bankAccountService.findBankAccountById(accountThree.getId()).get().getBalance());
    }

    @Test
    @DisplayName("Deposit greater than max deposit amount")
    void TestDepositGreaterThanMaxDepositAmount () {
        assertThrows(InvalidAmountException.class, ()->bankAccountService.deposit(11000, accountFive.getId()));
    }

    @Test
    @DisplayName("Deposit with nonexistent account")
    void TestDepositWithNonexistentAccount () {
        Long maxId = bankAccountRepository.findAll().stream()
                .mapToLong(BankAccount::getId)
                .max()
                .orElse(0L);
        Long testId = maxId + 1;
        assertThrows(AccountNotFoundException.class, ()->bankAccountService.deposit(500, testId));
    }

    @Test
    @DisplayName("Deposit with null account id")
    void TestDepositWithNullAccountId () {
        assertThrows(IllegalArgumentException.class, ()->bankAccountService.deposit(500, null));
    }

    @Test
    @DisplayName("Deposit with 0 amount")
    void TestDepositWithZeroAmount () {
        assertThrows(InvalidAmountException.class, ()->bankAccountService.deposit(0, accountFour.getId()));
    }

    @Test
    @DisplayName("Deposit with negative amount")
    void TestDepositWithNegativeAmount () {
        assertThrows(InvalidAmountException.class, ()->bankAccountService.deposit(-500, accountFour.getId()));
    }

    //Withdraw Tests
    @ParameterizedTest
    @CsvFileSource(resources = "/withdraw.csv", numLinesToSkip = 1)
    @DisplayName("Deposit Positive")
    void TestWithdrawPositive(double amount, double expectedBalance) {
        bankAccountService.withdraw(amount, accountThree.getId());
        assertEquals(expectedBalance, bankAccountService.findBankAccountById(accountThree.getId()).get().getBalance());
    }

    @Test
    @DisplayName("Withdraw with nonexistent account")
    void TestWithdrawWithNonexistentAccount () {Long maxId = bankAccountRepository.findAll().stream()
            .mapToLong(BankAccount::getId)
            .max()
            .orElse(0L);
        Long testId = maxId + 1;
        assertThrows(AccountNotFoundException.class, () -> bankAccountService.withdraw(500, testId));
    }

    @Test
    @DisplayName("Withdraw with null account id")
    void TestWithdrawWithNullAccountId () {
        assertThrows(IllegalArgumentException.class, ()->bankAccountService.withdraw(500, null));
    }

    @Test
    @DisplayName("Withdraw with 0 amount")
    void TestWithdrawWithZeroAmount () {
        assertThrows(InvalidAmountException.class, ()->bankAccountService.withdraw(0, accountFour.getId()));
    }

    @Test
    @DisplayName("Withdraw with negative amount")
    void TestWithdrawWithNegativeAmount () {
        assertThrows(InvalidAmountException.class, ()->bankAccountService.withdraw(-500, accountFour.getId()));
    }

    @Test
    @DisplayName("Withdraw greater than max withdraw amount")
    void TestWithdrawGreaterThanMaxWithdrawAmount () {
        assertThrows(InvalidAmountException.class, ()->bankAccountService.withdraw(7000, accountOne.getId()));
    }


    @Test
    @DisplayName("Withdraw greater than account balance")
    void TestWithdrawGreaterThanAccountBalance () {
        assertThrows(InvalidAmountException.class, ()->bankAccountService.withdraw(1001, accountTwo.getId()));
    }

    @Test
    @DisplayName("Minimal account balance test")
    void TestWithdrawToMinimalAccountBalance () {
        assertThrows(InvalidAmountException.class, ()->bankAccountService.withdraw(951, accountTwo.getId()));
    }
}
