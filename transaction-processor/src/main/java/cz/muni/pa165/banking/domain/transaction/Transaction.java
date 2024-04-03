package cz.muni.pa165.banking.domain.transaction;

import cz.muni.pa165.banking.domain.account.Account;
import cz.muni.pa165.banking.domain.money.Money;

public class Transaction {
    
    private final Account source;
    
    private final Account target;
    
    private final TransactionType type;
    
    private final Money amount;
    
    private final String detail;

    public Transaction(Account source, Account target, TransactionType type, Money amount, String detail) {
        this.source = source;
        this.target = target;
        this.type = type;
        this.amount = amount;
        this.detail = detail;
    }

    public Account getSource() {
        return source;
    }

    public Account getTarget() {
        return target;
    }
    
    public TransactionType getType() {
        return type;
    }

    public Money getAmount() {
        return amount;
    }

    public String getDetail() {
        return detail;
    }

}
