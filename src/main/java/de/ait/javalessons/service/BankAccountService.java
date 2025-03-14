package de.ait.javalessons.service;

import de.ait.javalessons.exceptions.AccountNotFoundException;
import de.ait.javalessons.exceptions.AccountNumberException;
import de.ait.javalessons.exceptions.InvalidAmountException;
import de.ait.javalessons.model.BankAccount;
import de.ait.javalessons.repositories.BankAccountRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;

    @Value("${bank.min-balance:0.0}")
    private double minBalance;

    @Value("${bank.max-withdraw-amount:5000}")
    private double maxWithdrawAmount;

    @Value("${bank.max-deposit-amount:10000}")
    private double maxDepositAmount;

    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    /**
     * Метод для получения информации о всех аккаунтах
     *
     * @return List содержащий все аккаунты
     */
    public List<BankAccount> getAllBankAccounts() {
        return bankAccountRepository.findAll();
    }

    /**
     * Метод для поиска аккаунта по id
     * @param id идентификатор аккаунта
     * @return найденный аккаунт
     */
    public Optional<BankAccount> findBankAccountById(Long id) {
        if (id == null){
            log.warn("id must not be null");
            throw new IllegalArgumentException("id must not be null");
        }
        log.info("findBankAccountById: {}", id);
        Optional<BankAccount> account = bankAccountRepository.findById(id);
        if (account.isEmpty()){
            log.info("Account with id {} was not found", id);
        }
        return account;
    }

    /**
     * Метод сохранения нового аккаунта
     * @param bankAccount новый аккаунт
     * @return сохраненный аккаунт
     */
    public BankAccount saveNewBankAccount(BankAccount bankAccount) {
        if (bankAccount == null) {
            throw new IllegalArgumentException("Bank account must not be null");
        }
        Optional<BankAccount> duplicateAccount = bankAccountRepository.findBankAccountByAccountNumber(bankAccount.getAccountNumber());
        if (duplicateAccount.isPresent()) {
            log.error("Account with number {} already exists", bankAccount.getAccountNumber());
            throw new AccountNumberException("Account with same number is already exists");
        }
        log.info("Saving new bank account: {}", bankAccount);
        return bankAccountRepository.save(bankAccount);
    }

    /**
     * Метод пополнения счета
     * @param amount сумма для пополнения
     * @param bankAccountId id пополняемого аккаунта
     * @return double - сумма на счету после пополнения
     */
    @Transactional
    public double deposit(double amount, Long bankAccountId){
        if (bankAccountId == null) {
            log.error("Bank account id is null");
            throw new IllegalArgumentException("Bank account id must not be null");
        }
        BankAccount bankAccount = bankAccountRepository.findById(bankAccountId)
                .orElseThrow(() -> new AccountNotFoundException("Bank account with id " + bankAccountId + " not found"));
        if(amount <= 0){
            log.error("Amount must be greater than 0");
            throw new InvalidAmountException("Amount must be greater than 0");
        }
        if(amount > maxDepositAmount){
            log.error("Amount is greater than the max deposit amount");
            throw new InvalidAmountException("Amount is greater than the max deposit amount");
        }
        //Округляем для избежания неточностей при операциях с дробными суммами
        //bankAccount.setBalance(bankAccount.getBalance() + amount);
        bankAccount.setBalance((double) Math.round((bankAccount.getBalance() + amount)*100)/100);
        BankAccount savedBankAccount = bankAccountRepository.save(bankAccount);
        return savedBankAccount.getBalance();
    }

    /**
     * Метод списания со счета
     * @param amount сумма к списанию
     * @param bankAccountId id аккаунта с которого должны списаться средства
     * @return сумма на счету после списания
     */
    @Transactional
    public double withdraw(double amount, Long bankAccountId){
        if (bankAccountId == null) {
            log.error("Bank account id is null");
            throw new IllegalArgumentException("Bank account id must not be null");
        }
        BankAccount bankAccount = bankAccountRepository.findById(bankAccountId)
                .orElseThrow(() -> new AccountNotFoundException("Bank account with id " + bankAccountId + " not found"));
        if(amount <= 0){
            log.error("Amount must be greater than 0");
            throw new InvalidAmountException("Amount must be greater than 0");
        }
        if(amount > bankAccount.getBalance()){
            log.error("Amount is greater than the current balance");
            throw new InvalidAmountException("Amount is greater than the current balance");
        }
        if(amount > maxWithdrawAmount){
            log.error("Amount is greater than the max withdraw amount");
            throw new InvalidAmountException("Amount is greater than the max withdraw amount");
        }
        if(bankAccount.getBalance() - amount < minBalance){
            log.error("The current balance is less than the minimum balance");
            throw new InvalidAmountException("The current balance is less than the minimum balance");
        }
            bankAccount.setBalance((double) Math.round((bankAccount.getBalance() - amount) * 100) / 100);
            BankAccount savedBankAccount = bankAccountRepository.save(bankAccount);
            return savedBankAccount.getBalance();
    }
}
