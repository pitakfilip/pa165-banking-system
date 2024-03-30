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
    private final String userId;
    private BigDecimal amount;
    private final List<Transaction> transactionList;

    //TODO do we want ids per user or global - now set per user - problem with refunds?
    private int nextTransactionID = 0;

    public Balance(String userId) {
        this.amount = new BigDecimal(0);
        this.transactionList = new ArrayList<>();
        this.userId = userId;
    }

    public void AddTransaction(BigDecimal amount, TransactionType type, String processId) {
        BigDecimal amountCopy = new BigDecimal(amount.byteValueExact());
        transactionList.add(new Transaction(type, amountCopy,
                Date.from(LocalDateTime.now().toInstant(ZoneOffset.of("+00:00"))), this.nextTransactionID, processId));
        this.nextTransactionID += 1;
        this.amount = this.amount.add(amountCopy);
    }

    public BigDecimal getAmount() {
        return new BigDecimal(amount.byteValueExact());
    }

    public List<Transaction> getTransactions() {
        return transactionList;
    }

    public Transaction GetTransaction(int id) throws RuntimeException {
        List<Transaction> result = transactionList.stream().filter(a -> a.getId() == id).toList();
        //TODO custom exception
        if (result.isEmpty()) {
            throw new RuntimeException("list has no tranaction with this id");
        }
        return result.get(0);
    }

    public boolean TransactionExists(int id) {
        List<Transaction> result = transactionList.stream().filter(a -> a.getId() == id).toList();
        return !result.isEmpty();
    }

    //TODO remake to uuid? - based on calls from other service
    public boolean RefundTransaction(int id) {
        if(!TransactionExists(id)){
            return false;
        }
        Transaction toRefund = GetTransaction(id);
        Transaction newTransation = new Transaction(toRefund.getType(), toRefund.getAmount().negate(),
                Date.from(Instant.now()), nextTransactionID, toRefund.getProcessId());
        nextTransactionID += 1;
        transactionList.add(newTransation);
        this.amount = this.amount.add(toRefund.getAmount().negate());
        return true;
    }

    public Transaction GetTransaction(String processId)throws RuntimeException {
        List<Transaction> result = transactionList.stream().filter(a -> Objects.equals(a.getProcessId(), processId)).toList();
        //TODO custom exception
        if (result.isEmpty()) {
            throw new RuntimeException("list has no tranaction with this id");
        }
        return result.get(0);
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
    public String getUserId() {
        return userId;
    }
}
