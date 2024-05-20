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
 * Handler for transaction of type ${@link cz.muni.pa165.banking.domain.transaction.TransactionType#TRANSFER}.
 * Customer may send a specified amount of money to a foreign account, where the currency of both accounts does
 * not have to be the same. The amount of money is calculated via ${@link cz.muni.pa165.banking.domain.money.CurrencyConverter}
 * using the target account's currency.
 */
public class TransferHandler extends ProcessHandler {
    
    @Override
    void evaluate(ProcessTransaction processTransaction, AccountService accountService, CurrencyConverter currencyConverter) {
        Account source = processTransaction.getSource();
        validateAccount(source, accountService);
        
        Account target = processTransaction.getTarget();
        validateAccount(target, accountService);

        Money money = processTransaction.getMoney();
        Currency currency = money.getCurrencyInstance();
        Currency sourceAccountCurrency = accountService.getAccountCurrency(source);
        
        BigDecimal sourceAmount = money.getAmount();
        if (sourceAmount.signum() < 0) {
            throw new UnexpectedValueException("Unable to send negative amount!");
        }
        if (sourceAmount.signum() == 0) {
            throw new UnexpectedValueException("Unable to send zero amount");
        }
        
        if (!currency.equals(sourceAccountCurrency)) {
            sourceAmount = currencyConverter.convertTo(currency, sourceAccountCurrency, sourceAmount);
        }
        
        if (!accountService.accountHasSufficientFunds(source, sourceAmount)) {
            throw new UnexpectedValueException("Insufficient balance for transaction");
        }
        
        sourceAmount = sourceAmount.multiply(BigDecimal.valueOf(-1L));
        
        Currency targetAccountCurrency = accountService.getAccountCurrency(target);
        BigDecimal targetAmount = money.getAmount();
        if (!currency.equals(targetAccountCurrency)) {
            targetAmount = currencyConverter.convertTo(currency, targetAccountCurrency, targetAmount);
        }
        
        accountService.publishAccountChange(processTransaction.getUuid(), TransactionType.TRANSFER, sourceAmount, source);
        accountService.publishAccountChange(processTransaction.getUuid(), TransactionType.TRANSFER, targetAmount, target);
    }

}
