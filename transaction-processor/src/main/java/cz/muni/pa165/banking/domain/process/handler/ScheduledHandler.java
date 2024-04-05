package cz.muni.pa165.banking.domain.process.handler;

import cz.muni.pa165.banking.domain.money.CurrencyConverter;
import cz.muni.pa165.banking.domain.process.ProcessTransaction;
import cz.muni.pa165.banking.domain.remote.AccountService;

/**
 * Scheduled payments vary
 */
public class ScheduledHandler extends ProcessHandler {
    
    @Override
    void evaluate(ProcessTransaction processTransaction, AccountService accountService, CurrencyConverter currencyConverter) {
        // TODO
    }

}
