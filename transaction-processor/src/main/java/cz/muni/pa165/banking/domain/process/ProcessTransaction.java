package cz.muni.pa165.banking.domain.process;

import cz.muni.pa165.banking.domain.account.Account;
import cz.muni.pa165.banking.domain.money.Money;
import cz.muni.pa165.banking.domain.transaction.Transaction;
import cz.muni.pa165.banking.domain.transaction.TransactionType;
import jakarta.persistence.Entity;

import java.util.UUID;

@Entity
public class ProcessTransaction extends Transaction {
    
    private UUID uuid;

    @Deprecated
    public ProcessTransaction() {
        // Hibernate
        super();
    }

    public ProcessTransaction(Account source, Account target, TransactionType type, Money amount, String detail, UUID uuid) {
        super(source, target, type, amount, detail);
        this.uuid = uuid;
    }


    /**
     * Return a copy of Process UUID, ensuring the UUID is not modified or replaced. 
     */
    public UUID getUuid() {
        return UUID.fromString(uuid.toString());
    }
    
    @Deprecated // hibernate
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
