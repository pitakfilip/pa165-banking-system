package cz.muni.pa165.banking.domain.balance;

import cz.muni.pa165.banking.domain.report.StatisticalReport;
import cz.muni.pa165.banking.domain.transaction.Transaction;
import cz.muni.pa165.banking.domain.transaction.TransactionType;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Martin Mojzis
 * class representing balance of 1 account - has all transactions of the account stored in itself
 */
@Entity
@Table(name = "balance")
public class Balance {

    @Id
    @NotNull
    @Column(name = "balance_id")
    private String accountId;

    @Column(name = "amount")
    private BigDecimal amount;

    @OneToMany(mappedBy = "balance")
    private List<Transaction> transactionList = new ArrayList<>();

    public Balance(String accountId) {
        this.amount = new BigDecimal(0);
        this.accountId = accountId;
    }
    public Balance() {
    }

    @Transactional
    public Transaction addTransaction(BigDecimal amount, TransactionType type, UUID processId) {
        this.amount = this.amount.add(amount);
        Transaction tr = new Transaction(type, amount,
                OffsetDateTime.now(), processId, this);
        transactionList.add(tr);
        return tr;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public List<Transaction> getTransactions() {
        return transactionList.stream().toList();
    }

    public Transaction getTransaction(UUID pid) throws RuntimeException {
        List<Transaction> result = transactionList.stream().filter(a -> Objects.equals(a.getProcessId(), pid)).toList();
        if (result.isEmpty()) {
            throw new RuntimeException("list has no tranaction with this id");
        }
        return result.get(0);
    }

    public boolean transactionExists(UUID pid) {
        List<Transaction> result = transactionList.stream().filter(a -> Objects.equals(a.getProcessId(), pid)).toList();
        return !result.isEmpty();
    }

    @Transactional
    public StatisticalReport getReport(OffsetDateTime after, OffsetDateTime before) {
        return new StatisticalReport(this.getData(after, before));
    }

    @Transactional
    public List<Transaction> getData(OffsetDateTime after, OffsetDateTime before) {
        return transactionList.stream().filter(a -> a.getDate().isAfter(after) && a.getDate().isBefore(before)).toList();
    }

    @Transactional
    public List<Transaction> getData(OffsetDateTime after, OffsetDateTime before, BigDecimal amountMin, BigDecimal amountMax, TransactionType type) {
        List<Transaction> result = this.getData(after, before);
        return result.stream()
                .filter(a -> a.getAmount().compareTo(amountMax) < 0 && a.getAmount().compareTo(amountMin) > 0 && a.getType() == type)
                .toList();
    }

    @Transactional
    public List<Transaction> getData(OffsetDateTime after, OffsetDateTime before, BigDecimal amountMin, BigDecimal amountMax) {
        List<Transaction> result = this.getData(after, before);
        return result.stream()
                .filter(a -> a.getAmount().compareTo(amountMax) < 0 && a.getAmount().compareTo(amountMin) > 0)
                .toList();
    }
    public String getAccountId() {
        return accountId;
    }

    @Transactional
    public List<Transaction> getData(OffsetDateTime from, OffsetDateTime to, TransactionType type) {
        List<Transaction> result = this.getData(from, to);
        return result.stream()
                .filter(a -> a.getType() == type)
                .toList();
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Balance balance)) return false;
        return Objects.equals(accountId, balance.accountId) && Objects.equals(amount, balance.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, amount);
    }

    @Override
    public String toString() {
        return "Balance{" +
                "accountId='" + accountId + '\'' +
                ", amount=" + amount +
                '}';
    }
}
