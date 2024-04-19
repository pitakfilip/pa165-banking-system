package cz.muni.pa165.banking.application.controller;

import cz.muni.pa165.banking.account.management.AccountApi;
import cz.muni.pa165.banking.account.management.dto.*;
import cz.muni.pa165.banking.application.facade.AccountFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

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
        AccountDto account;
        try{
            account = accountFacade.findById(accountId);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(account);
    }

    @Override
    public ResponseEntity<ScheduledPaymentsDto> getScheduledPayments(String accountNumber) {
        ScheduledPaymentsDto payments;
        try{
            payments = accountFacade.findScheduledPaymentsByNumber(accountNumber);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(payments);
    }

    @Override
    public ResponseEntity<ScheduledPaymentDto> schedulePayment(ScheduledPaymentDto scheduledPaymentDto) {
        return new ResponseEntity<>(accountFacade.schedulePayment(scheduledPaymentDto), HttpStatus.CREATED);
    }

}
