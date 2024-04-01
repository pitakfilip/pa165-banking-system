package cz.muni.pa165.banking.application.service;
import cz.muni.pa165.banking.application.repository.AccountRepositoryImpl;
import cz.muni.pa165.banking.domain.account.Account;
import cz.muni.pa165.banking.domain.scheduler.info.PaymentInfo;
import cz.muni.pa165.banking.domain.scheduler.jobs.PaymentJob;
import cz.muni.pa165.banking.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AccountService {
    private final SchedulerService scheduler;
    private final AccountRepositoryImpl accountRepository;
    @Autowired
    public AccountService(SchedulerService schedulerService, AccountRepositoryImpl accountRepository){
        scheduler = schedulerService;
        this.accountRepository = accountRepository;
    }
    public Account createAccount(User user){
        Account newAccount = new Account(UUID.randomUUID().toString(), user);
        while (!accountRepository.addAccount(newAccount)) {
            newAccount = new Account(UUID.randomUUID().toString(), user);
        }
        user.addAccount(newAccount.getId());
        return newAccount;
    }
    public Account getAccount(String id){
        return accountRepository.getById(id);
    }
    @Transactional
    public boolean payment(String senderAccountId, String receiverAccountId, Integer amount){
        Account sender = accountRepository.getById(senderAccountId);
        Account receiver = accountRepository.getById(receiverAccountId);
        if (sender == null || receiver == null || !sender.spendMoney(amount)){ return false; }
        receiver.receiveMoney(amount);
        return true;
    }
    public void schedulePayment (String senderAccountId, String receiverAccountId, Integer amount, Integer delay){
        final PaymentInfo info = new PaymentInfo();
        info.setSenderAccountId(senderAccountId);
        info.setReceiverAccountId(receiverAccountId);
        info.setMoneyAmount(amount);
        info.setRunForever(true);
        info.setInitialOffsetMs(delay);
        info.setRepeatIntervalMs(5000);

        scheduler.schedule(PaymentJob.class, info);
    }
    public Boolean deleteTimer(final String timerId) {
        return scheduler.deleteTimer(timerId);
    }
}
