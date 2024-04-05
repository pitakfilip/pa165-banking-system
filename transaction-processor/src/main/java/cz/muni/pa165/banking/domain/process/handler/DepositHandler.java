package cz.muni.pa165.banking.domain.process.handler;

import cz.muni.pa165.banking.domain.account.Account;
import cz.muni.pa165.banking.domain.money.CurrencyConverter;
import cz.muni.pa165.banking.domain.money.Money;
import cz.muni.pa165.banking.domain.process.ProcessTransaction;
import cz.muni.pa165.banking.domain.remote.AccountService;
import cz.muni.pa165.banking.exception.EntityNotFoundException;
import cz.muni.pa165.banking.exception.UnexpectedValueException;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * Handler for transaction of type ${@link cz.muni.pa165.banking.domain.transaction.TransactionType#DEPOSIT}.
 * Customer may deposit a specified amount of money of the same currency as the account is set to.
 */
class DepositHandler extends ProcessHandler {
    
    @Override
    void evaluate(ProcessTransaction processTransaction, AccountService accountService, CurrencyConverter currencyConverter) {
        Account account = processTransaction.getSource();
        if (!accountService.isValid(account)) {
            throw new EntityNotFoundException(
                    String.format("Account with number {%s} does not exist", account.getAccountNumber())
            );
        }

        Currency accountCurrency = accountService.getAccountCurrency(account);
        Money money = processTransaction.getMoney();
        if (!accountCurrency.equals(money.getCurrency())) {
            throw new UnexpectedValueException(String.format("Unable to deposit of provided currency (%s) as the account's currency is '%s'", money.getCurrency(), accountCurrency));    
        }
        
        BigDecimal convertedAmount = currencyConverter.convertTo(accountCurrency, processTransaction.getMoney());
        
        accountService.publishAccountChange(processTransaction.getUuid(), account, convertedAmount);
    }

}
