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
 * Handler for transaction of type ${@link cz.muni.pa165.banking.domain.transaction.TransactionType#WITHDRAW}.
 * Customer may withdraw a specified amount of money of the account's currency.
 */
public class WithdrawHandler extends ProcessHandler {

    @Override
    void evaluate(ProcessTransaction processTransaction, AccountService accountService, CurrencyConverter currencyConverter) {
        Account account = processTransaction.getSource();
        if (!accountService.isValid(account)) {
            throw new EntityNotFoundException(
                    String.format("Account with number {%s} does not exist", account.getAccountNumber())
            );
        }

        Money money = processTransaction.getMoney();
        Currency accountCurrency = accountService.getAccountCurrency(account);
        if (!accountCurrency.equals(money.getCurrency())) {
            throw new UnexpectedValueException(String.format("Unable to withdraw of provided currency (%s) as the account's currency is '%s'", money.getCurrency(), accountCurrency));
        }
        if (!accountService.accountHasSufficientFunds(account, money.getAmount())) {
            throw new EntityNotFoundException(
                    String.format(
                            "Account with number {%s} does not have sufficient funds for withdrawal of %s %s",
                            account.getAccountNumber(),
                            money.getAmount(),
                            money.getCurrency()
                    )
            );
        }

        BigDecimal convertedAmount = currencyConverter.convertTo(accountCurrency, money);
        BigDecimal calculatedAmount = convertedAmount.multiply(BigDecimal.valueOf(-1L));

        accountService.publishAccountChange(processTransaction.getUuid(), account, calculatedAmount);
    }

}
