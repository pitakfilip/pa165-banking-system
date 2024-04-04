package cz.muni.pa165.banking.application.api;

import cz.muni.pa165.banking.account.management.AccountApi;
import cz.muni.pa165.banking.account.management.dto.*;
import cz.muni.pa165.banking.application.facade.AccountFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController implements AccountApi {
    private final AccountFacade accountFacade;

    @Autowired
    public AccountController(AccountFacade accountFacade){
        this.accountFacade = accountFacade;
    }

    @Override
    public ResponseEntity<Account> createAccount(CreateAccountRequest createAccountRequest) {
        return ResponseEntity.ok(accountFacade.createAccount(createAccountRequest));
    }

    @Override
    public ResponseEntity<Account> getAccount(GetAccountRequest getAccountRequest) {
        return ResponseEntity.ok(accountFacade.getAccount(getAccountRequest));
    }

    @Override
    public ResponseEntity<ScheduledPayment> schedulePayment(SchedulePaymentRequest schedulePaymentRequest) {
        return ResponseEntity.ok(accountFacade.schedulePayment(schedulePaymentRequest));
    }
}
