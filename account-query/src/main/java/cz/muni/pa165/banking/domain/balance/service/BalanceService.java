package cz.muni.pa165.banking.domain.balance.service;

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
    boolean addNewBalance(String id);
    BigDecimal getBalance(String id);
    List<Transaction> getTransactions(String id, Date from, Date to);
    boolean addToBalance(String id, BigDecimal amount, String processID, TransactionType type);
}
