package cz.muni.pa165.banking.application.facade;

import cz.muni.pa165.banking.account.management.dto.*;
import cz.muni.pa165.banking.application.service.AccountService;
import org.springframework.stereotype.Service;

@Service
public class AccountFacade {
    
    private final AccountService accountService;

    public AccountFacade(AccountService accountService){
        this.accountService = accountService;
    }

    // TODO mapper pre convert DTO -> domain triedu, zavolat service co spracuje poziadavku, konvert domain trieda -> DTO, return DTO
    public AccountDto createAccount(NewAccountDto newAccountDto){
        Account account = mapper.mapFromDto(newAccountDto);
        return accountService.createAccount(account);
    }

    public AccountDto getAccount(String accountNumber){
        Account account = accountService.getAccount(accountNumber);
        return accountService.getAccount(mapper.mapToDto(account));
    }

    public ScheduledPaymentDto schedulePayment(ScheduledPaymentDto scheduledPaymentDto){
        ScheduledPayment newScheduledPayment = mapper.mapFromDto(scheduledPaymentDto);
        return accountService.schedulePayment(newScheduledPayment);
    }

}
