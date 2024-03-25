package cz.muni.pa165.banking.domain.balance;

import cz.muni.pa165.banking.domain.transaction.Transaction;
import cz.muni.pa165.banking.domain.transactionlist.TransactionList;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Martin Mojzis
 */
public class Balance {

    private BigDecimal amount;
    private final TransactionList transactions;

    public Balance() {
        this.amount = new BigDecimal(0);
        transactions = new TransactionList();
    }
    public boolean AddTransaction(Transaction transaction){
        try {
            //TODO make these operations sequential
            transactions.AddTransaction(transaction);
            this.amount = this.amount.add(transaction.getAmount());
        }
        catch (Exception e){
            return false;
        }
        return true;
    }
    public BigDecimal getAmount() {
        return amount;
    }

    public TransactionList getTransactions() {
        return transactions;
    }
    public boolean RefundTransaction(int id){
        try {
            Transaction toRefund = transactions.GetTransaction(id);
            Transaction newTransation = new Transaction(toRefund.getType(), toRefund.getAmount(), Date.from(Instant.now()));
            transactions.AddTransaction(newTransation);
        }
        catch (Exception e){
            return false;
        }
        return true;
    }
}
