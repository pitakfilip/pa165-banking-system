package cz.muni.pa165.banking.application.service;

import cz.muni.pa165.banking.application.repository.AccountRepositoryImpl;
import cz.muni.pa165.banking.application.repository.ScheduledPaymentRepositoryImpl;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccountService {
    
    private final AccountRepositoryImpl accountRepository;
    
    private final ScheduledPaymentRepositoryImpl scheduledPaymentsRepository;
    
    public AccountService(AccountRepositoryImpl accountRepository, ScheduledPaymentRepositoryImpl scheduledPaymentsRepository){
        this.accountRepository = accountRepository;
        this.scheduledPaymentsRepository = scheduledPaymentsRepository;
    }
    
    public Account createAccount(CreateAccountRequest createAccountRequest){
        User user = createAccountRequest.getUser();
        Account newAccount = new Account(UUID.randomUUID().toString(), user);

        while (!accountRepository.addAccount(newAccount)) {
            newAccount = new Account(UUID.randomUUID().toString(), user);
        }
        
        if (user != null) { 
            user.addAccountsItem(newAccount.getId()); 
        }
        
        return newAccount;
    }
    
    public Account getAccount(GetAccountRequest getAccountRequest){
        String id = getAccountRequest.getAccountId();
        return accountRepository.getById(id);
    }
    
    public ScheduledPayment schedulePayment (SchedulePaymentRequest schedulePaymentRequest){
        Account senderAccount = schedulePaymentRequest.getSenderAccount();
        if (senderAccount == null) {
            return null;
        }

        String receiverAccountId = schedulePaymentRequest.getReceiverAccountId();
        Integer amount = schedulePaymentRequest.getAmount();
        ScheduledPayment newScheduledPayment = new ScheduledPayment(UUID.randomUUID().toString(), senderAccount.getId(), receiverAccountId, amount);
        
        while (!scheduledPaymentsRepository.addScheduledPayment(newScheduledPayment)) {
            newScheduledPayment = new ScheduledPayment(UUID.randomUUID().toString(), senderAccount.getId(), receiverAccountId, amount);
        }
        senderAccount.addScheduledPaymentsItem(newScheduledPayment.getId());
        
        return newScheduledPayment;
    }

}
