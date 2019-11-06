package com.example.scooterRentalApp.service;

import com.example.scooterRentalApp.api.BasicResponse;
import com.example.scooterRentalApp.api.request.CreateUserAccountRequest;
import com.example.scooterRentalApp.api.response.CreateUserAccountResponse;
import com.example.scooterRentalApp.model.Scooter;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

public interface UserAccountService {

    ResponseEntity<CreateUserAccountResponse> createUserAccount(CreateUserAccountRequest request);
    ResponseEntity<BasicResponse> rechargeUserAccount(Long accountId, String amount);

    ResponseEntity<Scooter> scooterInfo(String ownerEmail);
    ResponseEntity<BigDecimal> balanceInfo(Long accountId);
    ResponseEntity<BasicResponse> deleteAccount(String ownerEmail);

    ResponseEntity<BasicResponse> changeEmail(Long accountId, String ownerEmail);
}
