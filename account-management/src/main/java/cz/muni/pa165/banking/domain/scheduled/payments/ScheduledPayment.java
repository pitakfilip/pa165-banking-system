package cz.muni.pa165.banking.domain.scheduled.payments;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public class ScheduledPayment {
    private String id;
    private String senderAccountId;
    private String receiverAccountId;
    private Integer amount;

    public ScheduledPayment() {
    }

    public ScheduledPayment(String id, String senderAccountId, String receiverAccountId, Integer amount) {
        this.id = id;
        this.senderAccountId = senderAccountId;
        this.receiverAccountId = receiverAccountId;
        this.amount = amount;
    }

    @JsonProperty("id")
    public @NotNull String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("senderAccountId")
    public @NotNull String getSenderAccountId() {
        return this.senderAccountId;
    }

    public void setSenderAccountId(String senderAccountId) {
        this.senderAccountId = senderAccountId;
    }

    @JsonProperty("receiverAccountId")
    public @NotNull String getReceiverAccountId() {
        return this.receiverAccountId;
    }

    public void setReceiverAccountId(String receiverAccountId) {
        this.receiverAccountId = receiverAccountId;
    }

    @JsonProperty("amount")
    public @NotNull Integer getAmount() {
        return this.amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
