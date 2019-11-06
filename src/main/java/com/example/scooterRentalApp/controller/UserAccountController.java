package com.example.scooterRentalApp.controller;

import com.example.scooterRentalApp.api.BasicResponse;
import com.example.scooterRentalApp.api.request.CreateUserAccountRequest;
import com.example.scooterRentalApp.api.response.CreateUserAccountResponse;
import com.example.scooterRentalApp.model.BalanceHistory;
import com.example.scooterRentalApp.model.Scooter;
import com.example.scooterRentalApp.service.BalanceService;
import com.example.scooterRentalApp.service.UserAccountService;
import com.example.scooterRentalApp.service.impl.UserAccountServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Set;

@RestController
@RequestMapping("user-account")
public class UserAccountController {

    private UserAccountService userAccountService;
    private BalanceService balanceService;

    public UserAccountController(UserAccountServiceImpl userAccountServiceImpl, BalanceService balanceService) {
        this.userAccountService = userAccountServiceImpl;
        this.balanceService = balanceService;
    }

    @PostMapping(value = "/create", produces = "application/json")
    public ResponseEntity<CreateUserAccountResponse> createUserAccount(
            @RequestBody CreateUserAccountRequest request
    ) {
        return userAccountService.createUserAccount(request);
    }

    @PutMapping(value = "/{accountId}/recharge", produces = "application/json")
    public ResponseEntity<BasicResponse> rechargeUserAccount(
            @PathVariable Long accountId,
            @RequestParam String amount
    ) {
        return userAccountService.rechargeUserAccount(accountId, amount);
    }

    @GetMapping(value = "/scooterInfo", produces = "application/json")
    public ResponseEntity<Scooter> scooterInfo(
            @RequestParam String ownerEmail
    ) {
        return userAccountService.scooterInfo(ownerEmail);
    }

    @GetMapping(value = "/balanceInfo", produces = "application/json")
    public ResponseEntity<BigDecimal> balanceInfo(
            @RequestParam Long accountId
    ) {
        return userAccountService.balanceInfo(accountId);
    }

    @GetMapping(value = "/balanceHistory", produces = "application/json")
    public ResponseEntity<Set<BalanceHistory>> balanceHistory(
            @RequestParam Long accountId
    ) {
        return balanceService.balanceHistory(accountId);
    }

    @DeleteMapping(value = "/{ownerEmail}", produces = "application/json")
    public ResponseEntity<BasicResponse> deleteAccount(
            @PathVariable String ownerEmail
    ) {
        return userAccountService.deleteAccount(ownerEmail);
    }

    @PutMapping(value = "/{accountId}/changeEmail", produces = "application/json")
    public ResponseEntity<BasicResponse> changeEmail(
            @PathVariable Long accountId,
            @RequestParam String ownerEmail
    ) {
        return userAccountService.changeEmail(accountId, ownerEmail);
    }

}
