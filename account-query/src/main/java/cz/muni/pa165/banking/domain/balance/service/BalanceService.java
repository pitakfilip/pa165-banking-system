package cz.muni.pa165.banking.domain.balance.service;

import cz.muni.pa165.banking.application.service.NotFoundAccountException;
import cz.muni.pa165.banking.domain.balance.Balance;
import cz.muni.pa165.banking.domain.transaction.Transaction;
import cz.muni.pa165.banking.domain.transaction.TransactionType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Martin Mojzis
 */
public interface BalanceService {
    void addNewBalance(String id) throws NotFoundAccountException;
    BigDecimal getBalance(String id) throws NotFoundAccountException;
    List<Transaction> getTransactions(String id, Date from, Date to) throws NotFoundAccountException;
    void addToBalance(String id, BigDecimal amount, String processID, TransactionType type) throws NotFoundAccountException;
}
