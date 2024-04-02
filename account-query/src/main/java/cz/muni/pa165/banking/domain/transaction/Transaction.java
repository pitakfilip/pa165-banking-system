package cz.muni.pa165.banking.domain.transaction;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Date;

/**
 * @author Martin Mojzis
 */
public class Transaction {
    private final TransactionType type;
    private final BigDecimal amount;
    private final OffsetDateTime date;
    private final String processId;

    public Transaction(TransactionType type, BigDecimal amount, OffsetDateTime date, String processId) {
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
    public String getProcessId() {
        return processId;
    }
}
