package cz.muni.pa165.banking.domain.account;

import cz.muni.pa165.banking.domain.scheduled.ScheduledPayment;
import jakarta.validation.Valid;

import java.util.List;

public class Account {

    private Long id;
    private String accountNumber;
    private Long userId;
    private Integer maxSpendingLimit;
    private AccountType type;
    private @Valid List<ScheduledPayment> scheduledPayments;

    public Account(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getMaxSpendingLimit() {
        return maxSpendingLimit;
    }

    public void setMaxSpendingLimit(Integer maxSpendingLimit) {
        this.maxSpendingLimit = maxSpendingLimit;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public List<ScheduledPayment> getScheduledPayments() {
        return scheduledPayments;
    }

    public void setScheduledPayments(List<ScheduledPayment> scheduledPayments) {
        this.scheduledPayments = scheduledPayments;
    }
}
