package cz.muni.pa165.banking.application.facade;

import cz.muni.pa165.banking.account.management.dto.AccountDto;
import cz.muni.pa165.banking.account.management.dto.NewAccountDto;
import cz.muni.pa165.banking.account.management.dto.ScheduledPaymentDto;
import cz.muni.pa165.banking.account.management.dto.ScheduledPaymentsDto;
import cz.muni.pa165.banking.application.mapper.DtoMapper;
import cz.muni.pa165.banking.application.service.AccountService;
import cz.muni.pa165.banking.domain.account.Account;
import cz.muni.pa165.banking.domain.scheduled.ScheduledPayment;
import cz.muni.pa165.banking.exception.EntityNotFoundException;
import cz.muni.pa165.banking.exception.UnexpectedValueException;
import org.springframework.stereotype.Component;

import java.util.Currency;


@Component
public class AccountFacade {

    private final AccountService accountService;
    private final DtoMapper mapper;

    public AccountFacade(AccountService accountService, DtoMapper mapper){
        this.accountService = accountService;
        this.mapper = mapper;
    }
    
    public AccountDto createAccount(NewAccountDto newAccountDto){
        String currencyCode = newAccountDto.getCurrency();
        try {
            Currency.getInstance(currencyCode);
        } catch (IllegalArgumentException e) {
            throw new UnexpectedValueException("Unsupported currency code '" + currencyCode + "'");
        }
        
        Account account = mapper.map(newAccountDto);
        return mapper.map(accountService.createAccount(account));
    }

    public AccountDto findById(Long accountId) throws EntityNotFoundException {
        return mapper.map(accountService.findById(accountId));
    }

    public ScheduledPaymentDto schedulePayment(ScheduledPaymentDto scheduledPaymentDto){
        ScheduledPayment newScheduledPayment = accountService.createNewScheduledPayment(
                scheduledPaymentDto.getSenderAccount(),
                scheduledPaymentDto.getReceiverAccount(),
                scheduledPaymentDto.getAmount(),
                mapper.map(scheduledPaymentDto.getType()),
                scheduledPaymentDto.getDay()
        );
        return mapper.mapScheduledPayment(newScheduledPayment, scheduledPaymentDto.getSenderAccount(), scheduledPaymentDto.getReceiverAccount());
    }

    public ScheduledPaymentsDto findScheduledPaymentsByNumber(String accountNumber){
        return mapper.map(accountService.findScheduledPaymentsByAccount(accountNumber));
    }
}
