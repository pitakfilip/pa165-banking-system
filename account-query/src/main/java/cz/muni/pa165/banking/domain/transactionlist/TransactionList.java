package cz.muni.pa165.banking.domain.transactionlist;

import cz.muni.pa165.banking.domain.transaction.Transaction;

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
    public void AddTransaction(Transaction transaction) throws Exception {
        //TODO custom exception
        if(!LIST.stream().filter(a -> a.getId() == transaction.getId()).toList().isEmpty()){
            throw new Exception("transaction with same id already in list");
        }
        transaction.setId(ID);
        ID += 1;
        LIST.add(transaction);
    }
    public Transaction GetTransaction(int id) throws Exception {
        List<Transaction> result = LIST.stream().filter(a -> a.getId() == id).toList();
        //TODO custom exception
        if (result.isEmpty()){
            throw new Exception("list has no tranaction with this id");
        }
        return result.get(0);
    }
    public List<Transaction> GetTransactionsBetween(Date after, Date before) {
        return LIST.stream().filter(a -> a.getDate().after(after) && a.getDate().before(before)).toList();
    }
}
