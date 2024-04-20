package cz.muni.pa165.banking.domain.scheduled;

import cz.muni.pa165.banking.domain.scheduled.recurrence.Recurrence;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
public class ScheduledPayment {
    
    @Id
    @NotNull
    @Column(name = "id")
    private Long id;

    @Column(name = "source_account_id")
    private Long sourceAccountId;

    @Column(name = "target_account_id")
    private Long targetAccountId;
    
    private BigDecimal amount;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "type", column = @Column(name = "recurrence_type")),
            @AttributeOverride(name = "paymentDay", column = @Column(name = "recurrence_payment_day"))
    })
    private Recurrence recurrence;
    

    public ScheduledPayment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSourceAccountId() {
        return sourceAccountId;
    }

    public void setSourceAccountId(Long sourceAccountId) {
        this.sourceAccountId = sourceAccountId;
    }

    public Long getTargetAccountId() {
        return targetAccountId;
    }

    public void setTargetAccountId(Long targetAccountId) {
        this.targetAccountId = targetAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Recurrence getRecurrence() {
        return recurrence;
    }

    public void setRecurrence(Recurrence recurrence) {
        this.recurrence = recurrence;
    }
}
