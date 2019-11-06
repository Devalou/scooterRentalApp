package com.example.scooterRentalApp.service;

import com.example.scooterRentalApp.model.BalanceHistory;
import com.example.scooterRentalApp.model.UserAccount;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Set;

public interface BalanceService {

    void saveBalanceToHistory(UserAccount userAccount, BigDecimal amount);

    ResponseEntity<Set<BalanceHistory>> balanceHistory(Long accountId);
}
