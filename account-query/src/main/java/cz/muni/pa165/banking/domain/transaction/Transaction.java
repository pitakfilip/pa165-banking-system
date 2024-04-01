package cz.muni.pa165.banking.domain.transaction;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Martin Mojzis
 */
public class Transaction {
    private final TransactionType type;
    private final BigDecimal amount;
    private final Date date;
    private final String processId;

    public Transaction(TransactionType type, BigDecimal amount, Date date, String processId) {
        this.type = type;
        this.amount = amount;
        this.date = date;
        this.processId = processId;
    }
    public Date getDate() {
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
