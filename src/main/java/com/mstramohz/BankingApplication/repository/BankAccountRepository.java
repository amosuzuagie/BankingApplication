package com.mstramohz.BankingApplication.repository;

import com.mstramohz.BankingApplication.entity.AccountUser;
import com.mstramohz.BankingApplication.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    BankAccount findByAccountNumber (String accountNumber);
    BankAccount findByUser (AccountUser user);
}
