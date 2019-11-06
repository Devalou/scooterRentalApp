package com.example.scooterRentalApp.service.impl;

import com.example.scooterRentalApp.api.BasicResponse;
import com.example.scooterRentalApp.api.request.CreateUserAccountRequest;
import com.example.scooterRentalApp.api.response.CreateUserAccountResponse;
import com.example.scooterRentalApp.common.MsgSource;
import com.example.scooterRentalApp.exception.CommonBadRequestException;
import com.example.scooterRentalApp.exception.CommonConflictException;
import com.example.scooterRentalApp.model.Scooter;
import com.example.scooterRentalApp.model.UserAccount;
import com.example.scooterRentalApp.repository.BalanceRepository;
import com.example.scooterRentalApp.repository.UserAccountRepository;
import com.example.scooterRentalApp.service.AbstractCommonService;
import com.example.scooterRentalApp.service.BalanceService;
import com.example.scooterRentalApp.service.UserAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.example.scooterRentalApp.common.ValidationUtils.*;

@Service
public class UserAccountServiceImpl extends AbstractCommonService implements UserAccountService {

    private UserAccountRepository userAccountRepository;
    private BalanceService balanceService;

    public UserAccountServiceImpl(MsgSource msgSource, UserAccountRepository userAccountRepository, BalanceRepository balanceRepository, BalanceService balanceService) {
        super(msgSource);
        this.userAccountRepository = userAccountRepository;
        this.balanceService = balanceService;
    }

    @Override
    public ResponseEntity<CreateUserAccountResponse> createUserAccount(CreateUserAccountRequest request) {
        validateCreateAccountRequest(request);
        checkOwnerEmailAlreadyExist(request.getOwnerEmail());
        UserAccount addedAccount = addUserAccountToDataSource(request);
        return ResponseEntity.ok(new CreateUserAccountResponse(msgSource.OK001, addedAccount.getId()));
    }

    private void validateCreateAccountRequest(CreateUserAccountRequest request) {
        if (isNullOrEmpty(request.getOwnerUsername())
                || isNullOrEmpty(request.getOwnerEmail())
                || isNull(request.getOwnerAge())) {

            throw new CommonBadRequestException(msgSource.ERR001);
        }
        if (isUncorrectedEmail(request.getOwnerEmail())) {
            throw new CommonBadRequestException(msgSource.ERR002);
        }
        if (isUncorrectedAge(request.getOwnerAge())) {
            throw new CommonConflictException(msgSource.ERR003);
        }
    }

    private UserAccount extractUserAccountFromRepository(Long accountId) {
        Optional<UserAccount> optionalUserAccount = userAccountRepository.findById(accountId);
        if (!optionalUserAccount.isPresent()) {
            throw new CommonConflictException(msgSource.ERR006);
        }
        return optionalUserAccount.get();
    }

    private void checkOwnerEmailAlreadyExist(String ownerEmail) {
        List<UserAccount> userAccounts = userAccountRepository.findByOwnerEmail(ownerEmail);
        if (!userAccounts.isEmpty()) {
            throw new CommonConflictException(msgSource.ERR004);
        }
    }

    private void checkOwnerEmailHasMoreAccounts(List<UserAccount> userAccounts){
        if (userAccounts==null || userAccounts.size()==0 || userAccounts.size()>1){
            throw new CommonConflictException(msgSource.ERR015);
        }
    }

    private UserAccount addUserAccountToDataSource(CreateUserAccountRequest request) {
        UserAccount userAccount = new UserAccount(
                null,
                request.getOwnerEmail(),
                request.getOwnerUsername(),
                request.getOwnerAge(),
                new BigDecimal(0.0),
                LocalDateTime.now()
        );
        return userAccountRepository.save(userAccount);
    }

    @Override
    public ResponseEntity<BasicResponse> rechargeUserAccount(Long accountId, String amount) {
        BigDecimal rechargeAmount = extractAmountToBigDecimal(amount);
        addRechargeAmountToUserAccountBalance(accountId, rechargeAmount);
        return ResponseEntity.ok(BasicResponse.of(msgSource.OK002));
    }

    @Override
    public ResponseEntity<BigDecimal> balanceInfo(Long accountId){
        BigDecimal balance = extractUserAccountFromRepository(accountId).getBalance();
        return ResponseEntity.ok(balance);
    }

    @Override
    public ResponseEntity<Scooter> scooterInfo(String ownerEmail) {
        List<UserAccount> userAccounts = userAccountRepository.findByOwnerEmail(ownerEmail);
        checkOwnerEmailHasMoreAccounts(userAccounts);
        UserAccount userAccount = userAccounts.get(0);
        Scooter scooter = userAccount.getScooter();
        return ResponseEntity.ok(scooter);
    }

    private BigDecimal extractAmountToBigDecimal(String amount) {
        try {
            return new BigDecimal(amount);
        } catch (NumberFormatException nfe) {
            throw new CommonBadRequestException(msgSource.ERR005);
        }
    }

    private void addRechargeAmountToUserAccountBalance(Long accountId, BigDecimal rechargeAmount) {
        Optional<UserAccount> userAccountData = userAccountRepository.findById(accountId);
        if (!userAccountData.isPresent()) {
            throw new CommonConflictException(msgSource.ERR006);
        }
        UserAccount accountData = userAccountData.get();
        accountData.setBalance(accountData.getBalance().add(rechargeAmount));
        balanceService.saveBalanceToHistory(accountData, rechargeAmount);
        userAccountRepository.save(accountData);
    }

    @Override
    public ResponseEntity<BasicResponse> deleteAccount(String ownerEmail) {
        List<UserAccount> userAccounts = userAccountRepository.findByOwnerEmail(ownerEmail);
        checkOwnerEmailHasMoreAccounts(userAccounts);
        UserAccount userAccount = userAccounts.get(0);
        userAccountRepository.delete(userAccount);
        return ResponseEntity.ok(BasicResponse.of(msgSource.OK006));
    }

    @Override
    @Transactional
    public ResponseEntity<BasicResponse> changeEmail(Long accountId, String ownerEmail) {
        if (isUncorrectedEmail(ownerEmail)) {
            throw new CommonBadRequestException(msgSource.ERR002);
        }
        checkOwnerEmailAlreadyExist(ownerEmail);
        UserAccount userAccount = extractUserAccountFromRepository(accountId);
        userAccount.setOwnerEmail(ownerEmail);
        userAccountRepository.save(userAccount);

        return ResponseEntity.ok(BasicResponse.of(msgSource.OK007));
    }
}



