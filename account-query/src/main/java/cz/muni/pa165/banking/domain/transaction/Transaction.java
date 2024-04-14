package cz.muni.pa165.banking.domain.transaction;

import cz.muni.pa165.banking.domain.balance.Balance;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Type;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * @author Martin Mojzis
 */
@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @NotNull
    Long id;

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "type", columnDefinition="SMALLINT check (type between 0 and 4)")
    private TransactionType type;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "date")
    private OffsetDateTime date;

    @Column(name = "process_id")
    private UUID processId;

    @ManyToOne
    @JoinColumn(name="balance_id", updatable = false)
    private Balance balance;

    public Transaction(TransactionType type, BigDecimal amount, OffsetDateTime date, UUID processId) {
        this.type = type;
        this.amount = amount;
        this.date = date;
        this.processId = processId;
    }

    public Transaction() {
    }

    public Transaction(TransactionType type, BigDecimal amount, OffsetDateTime date, UUID processId, Balance balance) {
        this.type = type;
        this.amount = amount;
        this.date = date;
        this.processId = processId;
        this.balance = balance;
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

    public void setBalance(Balance balance) {
        this.balance = balance;
    }

    public void setProcessId(UUID processId) {
        this.processId = processId;
    }

    public void setDate(OffsetDateTime date) {
        this.date = date;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }
}
