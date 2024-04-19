package cz.muni.pa165.banking.application.service;

import cz.muni.pa165.banking.domain.account.Account;
import cz.muni.pa165.banking.domain.account.repository.AccountRepository;
import cz.muni.pa165.banking.domain.scheduled.ScheduledPayment;
import cz.muni.pa165.banking.domain.scheduled.repository.ScheduledPaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    public Account findById(Long accountId) throws Exception{
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new Exception("Account with id: " + accountId + " was not found."));
    }
    
    public Account findByNumber(String accountNumber) throws Exception {
        return accountRepository.findByNumber(accountNumber)
                .orElseThrow(() -> new Exception("Account with number: " + accountNumber + " was not found."));
    }

    public ScheduledPayment schedulePayment(ScheduledPayment newScheduledPayment) {
        return scheduledPaymentsRepository.addScheduledPayment(newScheduledPayment);
    }

    public List<ScheduledPayment> findScheduledPaymentsById(Long accountId){
        return accountRepository.findScheduledPaymentsById(accountId);
    }

    public Map<Long, ScheduledPayment> getAllScheduledPayments(){
        return scheduledPaymentsRepository.getAllScheduledPayments();
    }
}
