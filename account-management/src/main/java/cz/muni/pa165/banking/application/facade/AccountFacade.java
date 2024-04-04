package cz.muni.pa165.banking.application.facade;

import cz.muni.pa165.banking.account.management.dto.*;
import cz.muni.pa165.banking.application.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountFacade {
    private final AccountService accountService;
    @Autowired
    public AccountFacade(AccountService accountService){
        this.accountService = accountService;
    }
    public Account createAccount(CreateAccountRequest createAccountRequest){
        return accountService.createAccount(createAccountRequest);
    }
    public Account getAccount(GetAccountRequest getAccountRequest){
        return accountService.getAccount(getAccountRequest);
    }
    public ScheduledPayment schedulePayment(SchedulePaymentRequest schedulePaymentRequest){
        return accountService.schedulePayment(schedulePaymentRequest);
    }
}
