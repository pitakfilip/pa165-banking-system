package cz.muni.pa165.banking.domain.money;

import java.math.BigDecimal;
import java.util.Currency;

public class Money {

    private BigDecimal amount;

    private Currency currency;

    @Deprecated // hibernate
    public Money(){}
    
    public Money(BigDecimal amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setCurrency(String currencyCode) {
        this.currency = Currency.getInstance(currencyCode);
    }
}
