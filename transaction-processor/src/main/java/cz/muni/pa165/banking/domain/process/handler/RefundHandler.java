package cz.muni.pa165.banking.domain.process.handler;


import cz.muni.pa165.banking.domain.account.Account;
import cz.muni.pa165.banking.domain.money.CurrencyConverter;
import cz.muni.pa165.banking.domain.money.Money;
import cz.muni.pa165.banking.domain.process.ProcessTransaction;
import cz.muni.pa165.banking.domain.remote.AccountService;
import cz.muni.pa165.banking.domain.transaction.TransactionType;

import java.math.BigDecimal;
import java.util.Currency;

public class RefundHandler extends ProcessHandler {

    @Override
    void evaluate(ProcessTransaction processTransaction, AccountService accountService, CurrencyConverter currencyConverter) {
        Account source = processTransaction.getSource();
        validateAccount(source, accountService);

        Account target = processTransaction.getTarget();
        validateAccount(target, accountService);

        Money money = processTransaction.getMoney();
        Currency sourceAccountCurrency = accountService.getAccountCurrency(source);
        BigDecimal sourceAmount = currencyConverter.convertTo(money.getCurrency(), sourceAccountCurrency, money.getAmount());

        Currency targetAccountCurrency = accountService.getAccountCurrency(target);
        BigDecimal targetAmount = currencyConverter.convertTo(money.getCurrency(), targetAccountCurrency, money.getAmount());
        targetAmount = targetAmount.multiply(BigDecimal.valueOf(-1L));

        accountService.publishAccountChange(processTransaction.getUuid(), TransactionType.REFUND, sourceAmount, source);
        accountService.publishAccountChange(processTransaction.getUuid(), TransactionType.REFUND, targetAmount, target);
    }
}
