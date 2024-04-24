package cz.muni.pa165.banking.application.controller;

import cz.muni.pa165.banking.account.management.AccountApi;
import cz.muni.pa165.banking.account.management.dto.*;
import cz.muni.pa165.banking.application.facade.AccountFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class AccountController implements AccountApi {
    
    private final AccountFacade accountFacade;

    public AccountController(AccountFacade accountFacade){
        this.accountFacade = accountFacade;
    }

    @Override
    public ResponseEntity<AccountDto> createAccount(NewAccountDto newAccountDto) {
        return new ResponseEntity<>(accountFacade.createAccount(newAccountDto), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<AccountDto> findAccountById (Long accountId) {
        return ResponseEntity.ok(accountFacade.findById(accountId));
    }

    @Override
    public ResponseEntity<AccountDto> findByAccountNumber(String accountNumber) {
        return ResponseEntity.ok(accountFacade.findByAccountNumber(accountNumber));
    }

    @Override
    public ResponseEntity<ScheduledPaymentsDto> getScheduledPayments(String accountNumber) {
        ScheduledPaymentsDto payments = accountFacade.findScheduledPaymentsByNumber(accountNumber);
        return ResponseEntity.ok(payments);
    }

    @Override
    public ResponseEntity<ScheduledPaymentsDto> getScheduledPaymentsOf(LocalDate date) {
        ScheduledPaymentsDto payments = accountFacade.scheduledPaymentsOfDay(date);
        return ResponseEntity.ok(payments);
    }

    @Override
    public ResponseEntity<ScheduledPaymentDto> schedulePayment(ScheduledPaymentDto scheduledPaymentDto) {
        return new ResponseEntity<>(accountFacade.schedulePayment(scheduledPaymentDto), HttpStatus.CREATED);
    }

}
