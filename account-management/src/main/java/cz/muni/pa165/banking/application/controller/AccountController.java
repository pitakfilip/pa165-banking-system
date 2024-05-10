package cz.muni.pa165.banking.application.controller;

import cz.muni.pa165.banking.account.management.AccountApi;
import cz.muni.pa165.banking.account.management.dto.*;
import cz.muni.pa165.banking.application.facade.AccountFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class AccountController implements AccountApi {
    
    private final AccountFacade accountFacade;

    public AccountController(AccountFacade accountFacade){
        this.accountFacade = accountFacade;
    }

    @Override
    @PreAuthorize("hasAnyAuthority('SCOPE_test_2', 'SCOPE_test_3')")
    public ResponseEntity<AccountDto> createAccount(NewAccountDto newAccountDto) {
        return new ResponseEntity<>(accountFacade.createAccount(newAccountDto), HttpStatus.CREATED);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('SCOPE_test_2', 'SCOPE_test_3')")
    public ResponseEntity<AccountDto> findAccountById (Long accountId) {
        return ResponseEntity.ok(accountFacade.findById(accountId));
    }

    @Override
    @PreAuthorize("hasAnyAuthority('SCOPE_test_2', 'SCOPE_test_3')")
    public ResponseEntity<AccountDto> findByAccountNumber(String accountNumber) {
        return ResponseEntity.ok(accountFacade.findByAccountNumber(accountNumber));
    }

    @Override
    @PreAuthorize("hasAnyAuthority('SCOPE_test_1', 'SCOPE_test_2', 'SCOPE_test_3')")
    public ResponseEntity<ScheduledPaymentsDto> getScheduledPayments(String accountNumber) {
        ScheduledPaymentsDto payments = accountFacade.findScheduledPaymentsByNumber(accountNumber);
        return ResponseEntity.ok(payments);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('SCOPE_test_2', 'SCOPE_test_3')")
    public ResponseEntity<ScheduledPaymentsDto> getScheduledPaymentsOf(LocalDate date) {
        ScheduledPaymentsDto payments = accountFacade.scheduledPaymentsOfDay(date);
        return ResponseEntity.ok(payments);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('SCOPE_test_1', 'SCOPE_test_2', 'SCOPE_test_3')")
    public ResponseEntity<ScheduledPaymentDto> schedulePayment(ScheduledPaymentDto scheduledPaymentDto) {
        return new ResponseEntity<>(accountFacade.schedulePayment(scheduledPaymentDto), HttpStatus.CREATED);
    }

}
