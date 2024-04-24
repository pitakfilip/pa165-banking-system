package cz.muni.pa165.banking.domain.scheduled;

import cz.muni.pa165.banking.domain.scheduled.recurrence.Recurrence;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "scheduled_payment")
public class ScheduledPayment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "source_account_id")
    private Long sourceAccountId;

    @Column(name = "target_account_id")
    private Long targetAccountId;
    
    private BigDecimal amount;

    @Column(name = "currency_code")
    private String currencyCode;
    
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

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Recurrence getRecurrence() {
        return recurrence;
    }

    public void setRecurrence(Recurrence recurrence) {
        this.recurrence = recurrence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduledPayment that = (ScheduledPayment) o;
        return Objects.equals(id, that.id) && Objects.equals(sourceAccountId, that.sourceAccountId) && Objects.equals(targetAccountId, that.targetAccountId) && Objects.equals(amount, that.amount) && Objects.equals(currencyCode, that.currencyCode) && Objects.equals(recurrence, that.recurrence);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sourceAccountId, targetAccountId, amount, currencyCode, recurrence);
    }

    @Override
    public String toString() {
        return "ScheduledPayment{" +
                "id=" + id +
                ", sourceAccountId=" + sourceAccountId +
                ", targetAccountId=" + targetAccountId +
                ", amount=" + amount +
                ", currencyCode='" + currencyCode + '\'' +
                ", recurrence=" + recurrence +
                '}';
    }
}
