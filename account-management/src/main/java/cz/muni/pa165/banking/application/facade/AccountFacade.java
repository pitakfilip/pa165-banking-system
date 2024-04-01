package cz.muni.pa165.banking.application.facade;

import cz.muni.pa165.banking.application.service.AccountService;
import cz.muni.pa165.banking.domain.account.Account;
import cz.muni.pa165.banking.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountFacade {
    private final AccountService accountService;
    @Autowired
    public AccountFacade(AccountService accountService){
        this.accountService = accountService;
    }
    public Account createAccount(User user){
        return accountService.createAccount(user);
    }
    public Account getAccount(String id){
        return accountService.getAccount(id);
    }
    public boolean payment(String senderAccountId, String receiverAccountId, Integer amount){
        return accountService.payment(senderAccountId, receiverAccountId, amount);
    }
    public void schedulePayment(String senderAccountId, String receiverAccountId, Integer amount, Integer delay){
        accountService.schedulePayment(senderAccountId, receiverAccountId, amount, delay);
    }
}
