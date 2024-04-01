package cz.muni.pa165.banking.domain.account;

import cz.muni.pa165.banking.domain.user.User;

import java.util.Map;

public class Account {
    private final String id;
    private int balance = 0;
    private final User owner;
    private int maxSpendingLimit = 5000;
    private Map<String, Integer> scheduledPayments;
    public Account(String id, User owner){
        this.id = id;
        this.owner = owner;
    }
    public boolean spendMoney(Integer amount){
        if (amount > maxSpendingLimit || amount > balance){return false;}
        balance -= amount;
        return true;
    }
    public void receiveMoney(Integer amount){
        balance += amount;
    }
    public void setMaxSpendingLimit(int limit){
        maxSpendingLimit = limit;
    }
    public String getId(){
        return id;
    }

    public Integer getBalance(){
        return balance;
    }
}
