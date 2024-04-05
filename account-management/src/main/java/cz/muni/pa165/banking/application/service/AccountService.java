package cz.muni.pa165.banking.application.service;

import cz.muni.pa165.banking.account.management.dto.AccountDto;
import cz.muni.pa165.banking.application.mapper.DtoMapper;
import cz.muni.pa165.banking.application.repository.AccountRepositoryImpl;
import cz.muni.pa165.banking.application.repository.ScheduledPaymentRepositoryImpl;
import cz.muni.pa165.banking.domain.account.Account;
import cz.muni.pa165.banking.domain.scheduled.payments.ScheduledPayment;
import cz.muni.pa165.banking.domain.scheduled.payments.ScheduledPayments;
import cz.muni.pa165.banking.domain.user.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AccountService {
    
    private final AccountRepositoryImpl accountRepository;
    
    private final ScheduledPaymentRepositoryImpl scheduledPaymentsRepository;
    
    public AccountService(AccountRepositoryImpl accountRepository, ScheduledPaymentRepositoryImpl scheduledPaymentsRepository){
        this.accountRepository = accountRepository;
        this.scheduledPaymentsRepository = scheduledPaymentsRepository;
    }
    
    public Account createAccount(Account newAccount){
        accountRepository.addAccount(newAccount);
        return newAccount;
    }
    
    public Account getAccount(String accountId){
        return accountRepository.getById(accountId);
    }
    
    public ScheduledPayment schedulePayment (ScheduledPayment newScheduledPayment){
        scheduledPaymentsRepository.addScheduledPayment(newScheduledPayment);
        return newScheduledPayment;
    }

    public List<ScheduledPayment> getScheduledPaymentsOfAccount(String accountId){
        Account senderAccount = accountRepository.getById(accountId);
        return senderAccount.getScheduledPayments();
    }
}
