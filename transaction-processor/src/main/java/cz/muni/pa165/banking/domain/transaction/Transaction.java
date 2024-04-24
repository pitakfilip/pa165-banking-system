package cz.muni.pa165.banking.domain.transaction;

import cz.muni.pa165.banking.domain.account.Account;
import cz.muni.pa165.banking.domain.money.Money;

import java.util.Objects;

public class Transaction {
    
    private Account source;
    
    private Account target;
    
    private TransactionType type;
    
    private Money money;
    
    private String detail;

    @Deprecated
    public Transaction() {
        // Hibernate
    }
    
    public Transaction(Account source, Account target,
                       TransactionType type, Money amount, String detail) {
        this.source = source;
        this.target = target;
        this.type = type;
        this.money = amount;
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

    public Money getMoney() {
        return money;
    }

    public String getDetail() {
        return detail;
    }

    @Deprecated // hibernate
    public void setSource(Account source) {
        this.source = source;
    }

    @Deprecated // hibernate
    public void setTarget(Account target) {
        this.target = target;
    }

    @Deprecated // hibernate
    public void setType(TransactionType type) {
        this.type = type;
    }

    @Deprecated // hibernate
    public void setMoney(Money amount) {
        this.money = amount;
    }

    @Deprecated // hibernate
    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction that)) return false;
        return Objects.equals(getSource(), that.getSource()) && Objects.equals(getTarget(), that.getTarget()) && getType() == that.getType() && Objects.equals(getMoney(), that.getMoney()) && Objects.equals(getDetail(), that.getDetail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSource(), getTarget(), getType(), getMoney(), getDetail());
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "source=" + source +
                ", target=" + target +
                ", type=" + type +
                ", money=" + money +
                ", detail='" + detail + '\'' +
                '}';
    }
}
