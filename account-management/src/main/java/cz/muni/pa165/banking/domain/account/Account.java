package cz.muni.pa165.banking.domain.account;

import jakarta.persistence.*;

import java.util.Currency;

@Entity
@Table(name = "bank_account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;
    @Column(name = "number")
    private String accountNumber;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "max_spending_limit")
    private Integer maxSpendingLimit;
    @Column(name = "type")
    private AccountType type;
    @Column(name = "currency")
    private Currency currency;

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

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Currency getCurrency() {
        return currency;
    }

}
