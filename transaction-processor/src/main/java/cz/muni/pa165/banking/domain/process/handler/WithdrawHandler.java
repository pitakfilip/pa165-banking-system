package cz.muni.pa165.banking.domain.process.handler;

import cz.muni.pa165.banking.domain.account.Account;
import cz.muni.pa165.banking.domain.money.Money;
import cz.muni.pa165.banking.domain.process.Process;
import cz.muni.pa165.banking.domain.process.ProcessTransaction;
import cz.muni.pa165.banking.domain.process.repository.HandlerMBeanRepository;
import cz.muni.pa165.banking.domain.process.repository.ProcessRepository;
import cz.muni.pa165.banking.domain.remote.AccountService;

import java.math.BigDecimal;
import java.util.Currency;

public class WithdrawHandler extends ProcessHandler {

    WithdrawHandler(ProcessRepository processRepository) {
        super(processRepository);
    }

    @Override
    void evaluate(Process process, HandlerMBeanRepository beans) {
        ProcessTransaction processTransaction = beans.processTransactionRepository().findTransactionByProcessId(process.uuid());

        Account account = processTransaction.getSource();
        AccountService accountService = beans.accountService();
        if (!accountService.isValid(account)) {
            throw new RuntimeException(
                    String.format("Account with number {%s} does not exist", account.getAccountNumber())
            );
        }

        Money money = processTransaction.getAmount();
        if (!accountService.accountHasSufficientFunds(account, money.getAmount())) {
            throw new RuntimeException(
                    String.format(
                            "Account with number {%s} does not have sufficient funds for withdrawal of %s %s",
                            account.getAccountNumber(),
                            money.getAmount(),
                            money.getCurrency()
                    )
            );
        }

        Currency accountCurrency = accountService.getAccountCurrency(account);
        BigDecimal convertedAmount = beans.currencyConverter().convertTo(accountCurrency, money);
        BigDecimal calculatedAmount = convertedAmount.multiply(BigDecimal.valueOf(-1L));

        accountService.publishAccountChange(processTransaction.getUuid(), account, calculatedAmount);
    }
}
