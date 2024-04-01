package cz.muni.pa165.banking.domain.balance;

import cz.muni.pa165.banking.domain.report.StatisticalReport;
import cz.muni.pa165.banking.domain.transaction.Transaction;
import cz.muni.pa165.banking.domain.transaction.TransactionType;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author Martin Mojzis
 */
public class Balance {
    private final String accountId;
    private BigDecimal amount;
    private final List<Transaction> transactionList;

    public Balance(String userId) {
        this.amount = new BigDecimal(0);
        this.transactionList = new ArrayList<>();
        this.accountId = userId;
    }

    public void AddTransaction(BigDecimal amount, TransactionType type, String processId) {
        BigDecimal amountCopy = new BigDecimal(amount.byteValueExact());
        transactionList.add(new Transaction(type, amountCopy,
                Date.from(LocalDateTime.now().toInstant(ZoneOffset.of("+00:00"))), processId));
        this.amount = this.amount.add(amountCopy);
    }

    public BigDecimal getAmount() {
        return new BigDecimal(amount.byteValueExact());
    }

    public List<Transaction> getTransactions() {
        return transactionList;
    }

    public Transaction GetTransaction(String pid) throws RuntimeException {
        List<Transaction> result = transactionList.stream().filter(a -> Objects.equals(a.getProcessId(), pid)).toList();
        if (result.isEmpty()) {
            throw new RuntimeException("list has no tranaction with this id");
        }
        return result.get(0);
    }

    public boolean TransactionExists(String pid) {
        List<Transaction> result = transactionList.stream().filter(a -> Objects.equals(a.getProcessId(), pid)).toList();
        return !result.isEmpty();
    }

    public StatisticalReport getReport(Date after, Date before) {
        return new StatisticalReport(this.getData(after, before));
    }

    public List<Transaction> getData(Date after, Date before) {
        return transactionList.stream().filter(a -> a.getDate().after(after) && a.getDate().before(before)).toList();
    }

    public List<Transaction> getData(Date after, Date before, BigDecimal amountMin, BigDecimal amountMax, TransactionType type) {
        List<Transaction> result = this.getData(after, before);
        return result.stream()
                .filter(a -> a.getAmount().compareTo(amountMax) < 0 && a.getAmount().compareTo(amountMin) > 0 && a.getType() == type)
                .toList();
    }

    public List<Transaction> getData(Date after, Date before, BigDecimal amountMin, BigDecimal amountMax) {
        List<Transaction> result = this.getData(after, before);
        return result.stream()
                .filter(a -> a.getAmount().compareTo(amountMax) < 0 && a.getAmount().compareTo(amountMin) > 0)
                .toList();
    }
    public String getAccountId() {
        return accountId;
    }
}
