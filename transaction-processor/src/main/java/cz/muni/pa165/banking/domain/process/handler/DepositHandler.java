package cz.muni.pa165.banking.domain.process.handler;

import cz.muni.pa165.banking.domain.account.Account;
import cz.muni.pa165.banking.domain.process.Process;
import cz.muni.pa165.banking.domain.process.ProcessTransaction;
import cz.muni.pa165.banking.domain.process.repository.HandlerMBeanRepository;
import cz.muni.pa165.banking.domain.process.repository.ProcessRepository;
import cz.muni.pa165.banking.domain.remote.AccountService;
import cz.muni.pa165.banking.exception.EntityNotFoundException;

import java.math.BigDecimal;
import java.util.Currency;

class DepositHandler extends ProcessHandler {
    
    DepositHandler(ProcessRepository processRepository) {
        super(processRepository);
    }

    @Override
    void evaluate(Process process, HandlerMBeanRepository beans) {
        ProcessTransaction processTransaction = beans.processTransactionRepository().findTransactionByProcessId(process.uuid());
        
        Account account = processTransaction.getSource();
        AccountService accountService = beans.accountService();
        if (!accountService.isValid(account)) {
            throw new EntityNotFoundException(
                    String.format("Account with number {%s} does not exist", account.getAccountNumber())
            );
        }

        Currency accountCurrency = accountService.getAccountCurrency(account);
        BigDecimal convertedAmount = beans.currencyConverter().convertTo(accountCurrency, processTransaction.getAmount());
        
        accountService.publishAccountChange(processTransaction.getUuid(), account, convertedAmount);
    }

}
