package cz.muni.pa165.banking.domain.process.handler;

import cz.muni.pa165.banking.domain.account.Account;
import cz.muni.pa165.banking.domain.money.CurrencyConverter;
import cz.muni.pa165.banking.domain.money.Money;
import cz.muni.pa165.banking.domain.process.ProcessTransaction;
import cz.muni.pa165.banking.domain.remote.AccountService;
import cz.muni.pa165.banking.domain.transaction.TransactionType;
import cz.muni.pa165.banking.exception.UnexpectedValueException;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * Handler for transaction of type ${@link cz.muni.pa165.banking.domain.transaction.TransactionType#SCHEDULED}.
 * Customer may send a specified amount of money to a foreign account automatically by setting a scheduled payment.
 * The implementation resembles ${@link CrossAccountHandler#evaluate} with minor variations.
 */
public class ScheduledHandler extends ProcessHandler {
    
    @Override
    void evaluate(ProcessTransaction processTransaction, AccountService accountService, CurrencyConverter currencyConverter) {
        Account source = processTransaction.getSource();
        validateAccount(source, accountService);

        Account target = processTransaction.getTarget();
        if (source.equals(target)) {
            throw new UnexpectedValueException("Transaction within a singular account not permitted");    
        }
        
        validateAccount(target, accountService);

        Money money = processTransaction.getMoney();
        Currency currency = money.getCurrency();
        Currency sourceAccountCurrency = accountService.getAccountCurrency(source);

        BigDecimal sourceAmount = money.getAmount();
        if (!currency.equals(sourceAccountCurrency)) {
            sourceAmount = currencyConverter.convertTo(currency, sourceAccountCurrency, sourceAmount);
        }
        sourceAmount = sourceAmount.multiply(BigDecimal.valueOf(-1L));

        Currency targetAccountCurrency = accountService.getAccountCurrency(target);
        BigDecimal targetAmount = money.getAmount();
        if (!currency.equals(targetAccountCurrency)) {
            targetAmount = currencyConverter.convertTo(currency, targetAccountCurrency, targetAmount);
        }

        accountService.publishAccountChange(processTransaction.getUuid(), TransactionType.SCHEDULED, sourceAmount, source, processTransaction.getDetail());
        accountService.publishAccountChange(processTransaction.getUuid(), TransactionType.SCHEDULED, targetAmount, target, processTransaction.getDetail());
    }

}
