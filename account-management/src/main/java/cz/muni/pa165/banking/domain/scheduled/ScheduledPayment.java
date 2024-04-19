package cz.muni.pa165.banking.domain.scheduled;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "scheduled_payment")
public class ScheduledPayment {
    @Id
    @NotNull
    @Column(name = "id")
    private Long id;
    @Column(name = "sender_account_id")
    private Long senderAccountId;
    @Column(name = "receiver_account_id")
    private Long receiverAccountId;
    @Column(name = "amount")
    private Integer amount;

    public ScheduledPayment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSenderAccountId() {
        return senderAccountId;
    }

    public void setSenderAccountId(Long senderAccountId) {
        this.senderAccountId = senderAccountId;
    }

    public Long getReceiverAccountId() {
        return receiverAccountId;
    }

    public void setReceiverAccountId(Long receiverAccountId) {
        this.receiverAccountId = receiverAccountId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

}
