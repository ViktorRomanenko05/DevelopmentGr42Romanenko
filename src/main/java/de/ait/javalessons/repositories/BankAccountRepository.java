package de.ait.javalessons.repositories;

import de.ait.javalessons.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    Optional<BankAccount> findBankAccountByAccountNumber(String accountNumber);

    boolean existsBankAccountByAccountNumber(String accountNumber);
}
