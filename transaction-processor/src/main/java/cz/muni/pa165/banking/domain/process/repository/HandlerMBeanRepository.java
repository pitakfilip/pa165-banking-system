package cz.muni.pa165.banking.domain.process.repository;

import cz.muni.pa165.banking.domain.money.CurrencyConverter;
import cz.muni.pa165.banking.domain.remote.AccountService;

public record HandlerMBeanRepository(AccountService accountService,
                                     ProcessTransactionRepository processTransactionRepository,
                                     CurrencyConverter currencyConverter) {

}
