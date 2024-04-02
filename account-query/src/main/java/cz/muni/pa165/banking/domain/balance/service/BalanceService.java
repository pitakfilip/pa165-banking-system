package cz.muni.pa165.banking.domain.balance.service;

import cz.muni.pa165.banking.application.exception.NotFoundAccountException;
import cz.muni.pa165.banking.domain.report.StatisticalReport;
import cz.muni.pa165.banking.domain.transaction.Transaction;
import cz.muni.pa165.banking.domain.transaction.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author Martin Mojzis
 */
public interface BalanceService {
    void addNewBalance(String id) throws NotFoundAccountException;
    BigDecimal getBalance(String id) throws NotFoundAccountException;
    List<Transaction> getTransactions(String id, OffsetDateTime from, OffsetDateTime to, BigDecimal minAmount, BigDecimal maxAmount, TransactionType type) throws NotFoundAccountException;
    List<Transaction> getTransactions(String id, OffsetDateTime from, OffsetDateTime to, BigDecimal minAmount, BigDecimal maxAmount) throws NotFoundAccountException;

    void addToBalance(String id, BigDecimal amount, String processID, TransactionType type) throws NotFoundAccountException;

    StatisticalReport getReport(String id,  OffsetDateTime beginning,  OffsetDateTime end);

    List<Transaction> getAllTransactions(OffsetDateTime from, OffsetDateTime from1, BigDecimal minAmount, BigDecimal maxAmount, TransactionType transactionType);
    List<Transaction> getAllTransactions(OffsetDateTime from, OffsetDateTime from1, BigDecimal minAmount, BigDecimal maxAmount);
}
