package cz.muni.pa165.banking;

import cz.muni.pa165.banking.application.service.AccountService;
import cz.muni.pa165.banking.application.service.UserService;
import cz.muni.pa165.banking.domain.account.Account;
import cz.muni.pa165.banking.domain.user.User;
import cz.muni.pa165.banking.dto.DtoUserType;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    private static AccountService accountService;
    private static UserService userService;
    @Autowired
    public Main(AccountService accountService, UserService userService) {
        this.accountService = accountService;
        this.userService = userService;
    }
    public static void main(String[] args) throws SchedulerException {
        SpringApplication.run(Main.class, args);
        User sender = userService.createUser("sender", "password", "sender@email.com", "Joe", "Mama", DtoUserType.REGULAR);
        User receiver = userService.createUser("receiver", "password", "receiver@email.com", "Joe", "Dada", DtoUserType.REGULAR);
        Account senderAccount = accountService.createAccount(sender);
        Account receiverAccount = accountService.createAccount(receiver);
        senderAccount.receiveMoney(2500);
        accountService.schedulePayment(senderAccount.getId(), receiverAccount.getId(), 1000, 5000);
    }
}