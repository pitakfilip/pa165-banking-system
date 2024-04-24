package cz.muni.pa165.banking.domain.money;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Objects.equals(getAmount(), money.getAmount()) && Objects.equals(getCurrency(), money.getCurrency());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAmount(), getCurrency());
    }

    @Override
    public String toString() {
        return "Money{" +
                "amount=" + amount +
                ", currency=" + currency +
                '}';
    }
}
