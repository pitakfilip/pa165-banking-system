package cz.muni.pa165.banking.domain.transactionlist;

import cz.muni.pa165.banking.domain.transaction.Transaction;
import cz.muni.pa165.banking.domain.transaction.TransactionType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Martin Mojzis
 */
public class TransactionList {
    //TODO maybe better to implement as dictionary
    private final List<Transaction> transactionList;
    private int transactionID = 0;

    public TransactionList() {
        transactionList = new ArrayList<>();
    }

    public void AddTransaction(Transaction transaction) throws RuntimeException {
        //TODO custom exception
        if (!transactionList.stream().filter(a -> a.getId() == transaction.getId()).toList().isEmpty()) {
            throw new RuntimeException("transaction with same id already in list");
        }
        transaction.setId(transactionID);
        transactionID += 1;
        transactionList.add(transaction);
    }

    public Transaction GetTransaction(int id) throws RuntimeException {
        List<Transaction> result = transactionList.stream().filter(a -> a.getId() == id).toList();
        //TODO custom exception
        if (result.isEmpty()) {
            throw new RuntimeException("list has no tranaction with this id");
        }
        return result.get(0);
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
}
