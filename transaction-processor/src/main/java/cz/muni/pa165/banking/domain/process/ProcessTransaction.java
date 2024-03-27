package cz.muni.pa165.banking.domain.process;

import cz.muni.pa165.banking.domain.account.Account;
import cz.muni.pa165.banking.domain.money.Money;
import cz.muni.pa165.banking.domain.transaction.Transaction;

public class ProcessTransaction extends Transaction {
    
    private final String uuid;
    
    ProcessTransaction(Account source, Account target, Money amount, String detail, String uuid) {
        super(source, target, amount, detail);
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }
}
