package cz.muni.pa165.banking.application.api;

import cz.muni.pa165.banking.application.facade.AccountFacade;
import cz.muni.pa165.banking.domain.account.Account;
import cz.muni.pa165.banking.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountApi {
    private final AccountFacade accountFacade;

    @Autowired
    public AccountApi(AccountFacade accountFacade){
        this.accountFacade = accountFacade;
    }
    public Account createAccount(User user){
        return accountFacade.createAccount(user);
    }
    public Account getAccount(String id){
        return accountFacade.getAccount(id);
    }
    public boolean payment(String senderAccountId, String receiverAccountId, Integer amount){
        return accountFacade.payment(senderAccountId, receiverAccountId, amount);
    }
    public void schedulePayment(String senderAccountId, String receiverAccountId, Integer amount, Integer delay){
        accountFacade.schedulePayment(senderAccountId, receiverAccountId, amount, delay);
    }
}
