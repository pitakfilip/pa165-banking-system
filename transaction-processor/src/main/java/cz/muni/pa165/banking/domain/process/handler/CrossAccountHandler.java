package cz.muni.pa165.banking.domain.process.handler;

import cz.muni.pa165.banking.domain.money.CurrencyConverter;
import cz.muni.pa165.banking.domain.process.ProcessTransaction;
import cz.muni.pa165.banking.domain.remote.AccountService;

/**
 * Handler for transaction of type ${@link cz.muni.pa165.banking.domain.transaction.TransactionType#WITHDRAW}.
 * Customer may send a specified amount of money to a foreign account, where the currency of both accounts does
 * not have to be the same. The amount of money is calculated via ${@link cz.muni.pa165.banking.domain.money.CurrencyConverter}
 * using the target account's currency.
 */
public class CrossAccountHandler extends ProcessHandler {
    
    @Override
    void evaluate(ProcessTransaction processTransaction, AccountService accountService, CurrencyConverter currencyConverter) {
        

    }

}
