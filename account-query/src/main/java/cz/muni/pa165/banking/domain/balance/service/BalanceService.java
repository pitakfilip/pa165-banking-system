package cz.muni.pa165.banking.domain.balance.service;

import cz.muni.pa165.banking.domain.report.StatisticalReport;
import cz.muni.pa165.banking.domain.transaction.Transaction;
import cz.muni.pa165.banking.domain.transaction.TransactionType;
import cz.muni.pa165.banking.exception.EntityNotFoundException;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @author Martin Mojzis
 */
public interface BalanceService {

    void addNewBalance(String id) throws EntityNotFoundException;

    BigDecimal getBalance(String id) throws EntityNotFoundException;

    List<Transaction> getTransactions(String id, OffsetDateTime from, OffsetDateTime to, BigDecimal minAmount,
                                      BigDecimal maxAmount, TransactionType type) throws EntityNotFoundException;

    void addToBalance(String id, BigDecimal amount, UUID processID, TransactionType type) throws EntityNotFoundException;

    StatisticalReport getReport(String id, OffsetDateTime beginning, OffsetDateTime end) throws EntityNotFoundException;

    List<Transaction> getAllTransactions(OffsetDateTime from, OffsetDateTime from1, BigDecimal minAmount,
                                         BigDecimal maxAmount, TransactionType transactionType);

    void deleteBalance(String id) throws EntityNotFoundException;
}
