package cz.muni.pa165.banking.application.facade;

import cz.muni.pa165.banking.account.management.dto.*;
import cz.muni.pa165.banking.application.mapper.DtoMapper;
import cz.muni.pa165.banking.application.service.AccountService;
import cz.muni.pa165.banking.domain.account.Account;
import cz.muni.pa165.banking.domain.scheduled.ScheduledPayment;
import org.springframework.stereotype.Component;


@Component
public class AccountFacade {

    private final AccountService accountService;
    private final DtoMapper mapper;

    public AccountFacade(AccountService accountService, DtoMapper mapper){
        this.accountService = accountService;
        this.mapper = mapper;
    }
    
    public AccountDto createAccount(NewAccountDto newAccountDto){
        Account account = mapper.map(newAccountDto);
        return mapper.map(accountService.createAccount(account));
    }

    public AccountDto getAccount(String accountNumber){
        return mapper.map(accountService.getAccountByNumber(accountNumber));
    }

    public ScheduledPaymentDto schedulePayment(ScheduledPaymentDto scheduledPaymentDto){
        ScheduledPayment newScheduledPayment = mapper.map(scheduledPaymentDto);
        return mapper.map(accountService.schedulePayment(newScheduledPayment));
    }
    public ScheduledPaymentsDto getScheduledPaymentsOfAccount(String accountNumber){
        Account account = accountService.getAccountByNumber(accountNumber);
        if (account == null) {
            throw new RuntimeException(String.format("Account with account number {%s} not found", accountNumber));
        }
        
        return mapper.map(accountService.getScheduledPaymentsOfAccount(account.getId()));
    }
}
