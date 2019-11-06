package com.example.scooterRentalApp.service.impl;

import com.example.scooterRentalApp.common.MsgSource;
import com.example.scooterRentalApp.exception.CommonConflictException;
import com.example.scooterRentalApp.model.BalanceHistory;
import com.example.scooterRentalApp.model.UserAccount;
import com.example.scooterRentalApp.repository.BalanceRepository;
import com.example.scooterRentalApp.repository.UserAccountRepository;
import com.example.scooterRentalApp.service.AbstractCommonService;
import com.example.scooterRentalApp.service.BalanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Service
public class BalanceServiceImpl extends AbstractCommonService implements BalanceService {
        private BalanceRepository balanceRepository;
        private UserAccountRepository userAccountRepository;

    public BalanceServiceImpl(MsgSource msgSource, BalanceRepository balanceRepository, UserAccountRepository userAccountRepository) {
        super(msgSource);
        this.balanceRepository = balanceRepository;
        this.userAccountRepository = userAccountRepository;
    }

    private UserAccount extractUserAccountFromRepository(Long accountId) {
        Optional<UserAccount> optionalUserAccount = userAccountRepository.findById(accountId);
        if (!optionalUserAccount.isPresent()) {
            throw new CommonConflictException(msgSource.ERR006);
        }
        return optionalUserAccount.get();
    }

    @Override
    public void saveBalanceToHistory(UserAccount userAccount, BigDecimal rechargeAmount) {
        BalanceHistory balanceHistory = new BalanceHistory(LocalDateTime.now(), rechargeAmount, userAccount.getBalance(),userAccount);
        balanceRepository.save(balanceHistory);
    }

    @Override
    public ResponseEntity<Set<BalanceHistory>> balanceHistory(Long accountId) {
        UserAccount userAccount = extractUserAccountFromRepository(accountId);
        Set<BalanceHistory> balances = balanceRepository.findByUserAccountOrderByIdDesc(userAccount);
        return ResponseEntity.ok(balances);
    }
}
