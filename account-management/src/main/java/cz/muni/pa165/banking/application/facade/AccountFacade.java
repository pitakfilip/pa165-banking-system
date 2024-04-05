package cz.muni.pa165.banking.application.facade;

import cz.muni.pa165.banking.account.management.dto.*;
import cz.muni.pa165.banking.application.mapper.DtoMapper;
import cz.muni.pa165.banking.application.service.AccountService;
import cz.muni.pa165.banking.domain.account.Account;
import cz.muni.pa165.banking.domain.scheduled.payments.ScheduledPayment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.DataTruncation;
import java.util.List;

@Component
public class AccountFacade {

    private final AccountService accountService;
    private final DtoMapper mapper;

    @Autowired
    public AccountFacade(AccountService accountService, DtoMapper mapper){
        this.accountService = accountService;
        this.mapper = mapper;
    }

    // TODO mapper pre convert DTO -> domain triedu, zavolat service co spracuje poziadavku, konvert domain trieda -> DTO, return DTO
    public AccountDto createAccount(NewAccountDto newAccountDto){
        Account account = mapper.map(newAccountDto);
        return mapper.map(accountService.createAccount(account));
    }

    public AccountDto getAccount(String accountNumber){
        Account account = accountService.getAccount(accountNumber);
        return mapper.map(accountService.getAccount(account.getId()));
    }

    public ScheduledPaymentDto schedulePayment(ScheduledPaymentDto scheduledPaymentDto){
        ScheduledPayment newScheduledPayment = mapper.map(scheduledPaymentDto);
        return mapper.map(accountService.schedulePayment(newScheduledPayment));
    }
    public ScheduledPaymentsDto getScheduledPaymentsOfAccount(String accountId){
        return mapper.map(accountService.getScheduledPaymentsOfAccount(accountId));
    }
}
