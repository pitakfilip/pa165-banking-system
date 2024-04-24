package cz.muni.pa165.banking.application.service;

import cz.muni.pa165.banking.account.management.AccountApi;
import cz.muni.pa165.banking.account.management.dto.AccountDto;
import cz.muni.pa165.banking.account.query.CustomerServiceApi;
import cz.muni.pa165.banking.account.query.SystemServiceApi;
import cz.muni.pa165.banking.domain.account.Account;
import cz.muni.pa165.banking.domain.remote.AccountService;
import cz.muni.pa165.banking.domain.transaction.TransactionType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {
    
    private final AccountApi accountApi;
    
    private final CustomerServiceApi publicBalanceApi;
    
    private final SystemServiceApi privateBalanceApi;

    public AccountServiceImpl(AccountApi accountApi, CustomerServiceApi publicBalanceApi, SystemServiceApi privateBalanceApi) {
        this.accountApi = accountApi;
        this.publicBalanceApi = publicBalanceApi;
        this.privateBalanceApi = privateBalanceApi;
    }

    @Override
    public Currency getAccountCurrency(Account account) {
        AccountDto accountDto = accountApi.findByAccountNumber(account.getAccountNumber()).getBody();
        Objects.requireNonNull(accountDto);
        return Currency.getInstance(accountDto.getCurrency());
    }

    @Override
    public boolean isValid(Account account) {
        AccountDto accountDto = accountApi.findByAccountNumber(account.getAccountNumber()).getBody();
        return accountDto != null;
    }

    @Override
    public boolean accountHasSufficientFunds(Account account, BigDecimal amount) {
        BigDecimal currentBalance = publicBalanceApi.getBalance(account.getAccountNumber()).getBody();
        return Objects.requireNonNull(currentBalance).compareTo(amount) >= 0;
    }

    @Override
    public void publishAccountChange(UUID processUuid, TransactionType transactionType, BigDecimal amount, Account account) {
        privateBalanceApi.addTransactionToBalance(
                account.getAccountNumber(),
                amount,
                processUuid,
                convertType(transactionType)
        );
    }
    
    private cz.muni.pa165.banking.account.query.dto.TransactionType convertType(TransactionType type) {
        return cz.muni.pa165.banking.account.query.dto.TransactionType.valueOf(type.name());
    }
}
