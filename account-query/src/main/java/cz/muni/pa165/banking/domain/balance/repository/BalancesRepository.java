package cz.muni.pa165.banking.domain.balance.repository;

import cz.muni.pa165.banking.domain.balance.Balance;
import cz.muni.pa165.banking.domain.transaction.Transaction;
import cz.muni.pa165.banking.domain.transaction.TransactionType;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Martin Mojzis
 */
public interface BalancesRepository {

    Optional<Balance> findById(String id);
    void addBalance(String id);

    boolean addNewBalance(String id);

    BigDecimal getBalance(String id);
    List<Transaction> getTransactions(String id, OffsetDateTime from, OffsetDateTime to, BigDecimal zero, BigDecimal maxAmount);
    List<Transaction> getTransactions(String id, OffsetDateTime from, OffsetDateTime to, BigDecimal minAmount, BigDecimal maxAmount, TransactionType type);
    void addToBalance(String id, BigDecimal amount, String processID, TransactionType type);
}
