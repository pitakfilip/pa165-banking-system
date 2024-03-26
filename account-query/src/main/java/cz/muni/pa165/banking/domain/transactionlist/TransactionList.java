package cz.muni.pa165.banking.domain.transactionlist;

import cz.muni.pa165.banking.domain.transaction.Transaction;
import cz.muni.pa165.banking.domain.transaction.TransactionType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Martin Mojzis
 */
public class TransactionList {
    //TODO maybe better to implement as dictionary
    private final List<Transaction> LIST;
    private int ID = 0;
    public TransactionList(){
        LIST = new ArrayList<>();
    }
    public void AddTransaction(Transaction transaction) throws RuntimeException {
        //TODO custom exception
        if(!LIST.stream().filter(a -> a.getId() == transaction.getId()).toList().isEmpty()){
            throw new RuntimeException("transaction with same id already in list");
        }
        transaction.setId(ID);
        ID += 1;
        LIST.add(transaction);
    }
    public Transaction GetTransaction(int id) throws RuntimeException {
        List<Transaction> result = LIST.stream().filter(a -> a.getId() == id).toList();
        //TODO custom exception
        if (result.isEmpty()){
            throw new RuntimeException("list has no tranaction with this id");
        }
        return result.get(0);
    }
    public List<Transaction> getData(Date after, Date before) {
        return LIST.stream().filter(a -> a.getDate().after(after) && a.getDate().before(before)).toList();
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
