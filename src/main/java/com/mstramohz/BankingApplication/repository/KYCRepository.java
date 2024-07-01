package com.mstramohz.BankingApplication.repository;

import com.mstramohz.BankingApplication.entity.KnowYourCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KYCRepository extends JpaRepository<KnowYourCustomer, Integer> {
}
