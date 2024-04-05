package cz.muni.pa165.banking.domain.transaction;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * @author Martin Mojzis
 */
public class Transaction {
    
    private final TransactionType type;
    
    private final BigDecimal amount;
    
    private final OffsetDateTime date;
    
    private final UUID processId;

    public Transaction(TransactionType type, BigDecimal amount, OffsetDateTime date, UUID processId) {
        this.type = type;
        this.amount = amount;
        this.date = date;
        this.processId = processId;
    }
    
    public OffsetDateTime getDate() {
        return date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }
    
    public UUID getProcessId() {
        return processId;
    }
}
