package cz.muni.pa165.banking.domain.remote;

import cz.muni.pa165.banking.domain.account.Account;
import cz.muni.pa165.banking.domain.transaction.TransactionType;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

/**
 * Interface serves as a proxy to a remote service  
 */
public interface AccountService {

    /**
     * Fetch currency of specified account  
     */
    Currency getAccountCurrency(Account account);

    /**
     * Validate existence of account
     */
    boolean isValid(Account account);

    /**
     * Validate sufficiency of funds for an account 
     */
    boolean accountHasSufficientFunds(Account account, BigDecimal amount);
    
    /**
     *  Publish transaction results to update balance of account within processed transaction
     */
    void publishAccountChange(UUID processUuid, TransactionType transactionType, BigDecimal amount, Account account, String information);
    
}
