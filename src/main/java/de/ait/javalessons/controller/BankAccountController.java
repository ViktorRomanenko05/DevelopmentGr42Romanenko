package de.ait.javalessons.controller;


import de.ait.javalessons.model.BankAccount;
import de.ait.javalessons.service.BankAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/accounts")
public class BankAccountController {

    private BankAccountService bankAccountService;

    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping
    public ResponseEntity<List<BankAccount>> getAllBankAccounts() {
        List<BankAccount> bankAccounts = bankAccountService.getAllBankAccounts();
        log.info("Found {} bank accounts", bankAccounts.size());
        return ResponseEntity.ok(bankAccounts);
    }

    @PostMapping
    public ResponseEntity<BankAccount> createBankAccount(@RequestParam String accountNumber,
                                                         @RequestParam String ownerName) {
        BankAccount bankAccount = new BankAccount(accountNumber,ownerName,0.0);
        BankAccount savedBankAccount = bankAccountService.saveNewBankAccount(bankAccount);
        if(savedBankAccount == null){
            log.error("Error saving bank account");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        log.info("Saved bank account with id {}", savedBankAccount.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBankAccount);
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<BankAccount> getBankAccountById(@RequestParam Long id){
        log.info("Getting bank account with id {}", id);
        return bankAccountService.findBankAccountById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PostMapping("/{id}/deposit")
    public ResponseEntity<Double> deposit(@PathVariable Long id, @RequestParam double amount){
        log.info("Depositing {} to bank account with id {}", amount, id);
        return ResponseEntity.ok(bankAccountService.deposit(amount, id));
    }

    @PostMapping("/{id}/withdraw")
    public ResponseEntity<Double> withdraw(@PathVariable Long id, @RequestParam double amount){
        log.info("Withdrawing {} from bank account with id {}", amount, id);
        return ResponseEntity.ok(bankAccountService.withdraw(amount, id));
    }
}
