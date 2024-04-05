package cz.muni.pa165.banking.application.service;

import cz.muni.pa165.banking.domain.account.Account;
import cz.muni.pa165.banking.domain.remote.AccountService;
import cz.muni.pa165.banking.domain.transaction.TransactionType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {
    
    // TODO add proxies to other services of teammates and calls with validations
    //  -> Milestone2
    
    @Override
    public Currency getAccountCurrency(Account account) {
        return Currency.getInstance("EUR");
    }

    @Override
    public boolean isValid(Account account) {
        return true;
    }

    @Override
    public boolean accountHasSufficientFunds(Account account, BigDecimal amount) {
        return true;
    }

    @Override
    public void publishAccountChange(UUID processUuid, TransactionType transactionType, BigDecimal amount, Account account, String information) {

    }
}
