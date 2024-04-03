package cz.muni.pa165.banking.application.service;

import cz.muni.pa165.banking.domain.account.Account;
import cz.muni.pa165.banking.domain.remote.AccountService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {
    // TODO add proxies to other services of teammates
    
    @Override
    public Currency getAccountCurrency(Account account) {
        return null;
    }

    @Override
    public boolean isValid(Account account) {
        return false;
    }

    @Override
    public boolean accountHasSufficientFunds(Account account, BigDecimal amount) {
        return false;
    }

    @Override
    public void publishAccountChange(UUID processUuid, Account account, BigDecimal amount) {

    }
}
