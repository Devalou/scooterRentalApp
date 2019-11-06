package com.example.scooterRentalApp.repository;

import com.example.scooterRentalApp.model.BalanceHistory;
import com.example.scooterRentalApp.model.UserAccount;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface BalanceRepository extends CrudRepository<BalanceHistory, Long> {
    Set<BalanceHistory> findByUserAccountOrderByIdDesc(UserAccount userAccount);
}
