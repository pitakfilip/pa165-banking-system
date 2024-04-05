package cz.muni.pa165.banking.application.service;

import cz.muni.pa165.banking.domain.account.Account;
import cz.muni.pa165.banking.domain.account.repository.AccountRepository;
import cz.muni.pa165.banking.domain.scheduled.ScheduledPayment;
import cz.muni.pa165.banking.domain.scheduled.repository.ScheduledPaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    
    private final AccountRepository accountRepository;
    
    private final ScheduledPaymentRepository scheduledPaymentsRepository;
    
    public AccountService(AccountRepository accountRepository, ScheduledPaymentRepository scheduledPaymentsRepository){
        this.accountRepository = accountRepository;
        this.scheduledPaymentsRepository = scheduledPaymentsRepository;
    }
    
    public Account createAccount(Account newAccount){
        return accountRepository.addAccount(newAccount);
    }

    public Account getAccount(Long accountId) {
        return accountRepository.getById(accountId);
    }
    
    public Account getAccountByNumber(String accountNumber) {
        return accountRepository.getByAccountNumber(accountNumber);
    }

    public ScheduledPayment schedulePayment(ScheduledPayment newScheduledPayment) {
        return scheduledPaymentsRepository.addScheduledPayment(newScheduledPayment);
    }

    public List<ScheduledPayment> getScheduledPaymentsOfAccount(Long accountId) {
        Account senderAccount = accountRepository.getById(accountId);
        return senderAccount.getScheduledPayments();
    }
}
