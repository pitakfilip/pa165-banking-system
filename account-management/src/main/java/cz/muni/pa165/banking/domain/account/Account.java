package cz.muni.pa165.banking.domain.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.muni.pa165.banking.account.management.dto.AccountTypeDto;
import cz.muni.pa165.banking.domain.scheduled.payments.ScheduledPayment;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private String id;
    private Long userId;
    private Integer maxSpendingLimit;
    private AccountType type;
    private @Valid List<ScheduledPayment> scheduledPayments;

    public Account(){}

    public Account(String id, Long userId) {
        this.id = id;
        this.userId = userId;
        this.scheduledPayments = new ArrayList<>();
    }

    @JsonProperty("id")
    public @NotNull String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("owner")
    public @NotNull @Valid Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @JsonProperty("maxSpendingLimit")
    public Integer getMaxSpendingLimit() {
        return this.maxSpendingLimit;
    }

    public void setMaxSpendingLimit(Integer maxSpendingLimit) {
        this.maxSpendingLimit = maxSpendingLimit;
    }

    @Valid
    @JsonProperty("type")
    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    @JsonProperty("scheduledPayments")
    public List<ScheduledPayment> getScheduledPayments() {
        return this.scheduledPayments;
    }

    public void setScheduledPayments(List<ScheduledPayment> scheduledPayments) {
        this.scheduledPayments = scheduledPayments;
    }

    public void addScheduledPaymentsItem(ScheduledPayment scheduledPayment) {
        this.scheduledPayments.add(scheduledPayment);
    }
}
