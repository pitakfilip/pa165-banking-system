package cz.muni.pa165.banking.domain.money;

import cz.muni.pa165.banking.domain.money.exchange.ExchangeRateService;

import java.math.BigDecimal;
import java.util.Currency;

public class CurrencyConverter {
    
    private final ExchangeRateService exchangeRateApi;
    
    public CurrencyConverter(ExchangeRateService exchangeRateApi) {
        this.exchangeRateApi = exchangeRateApi;
    }

    /**
     * Converts a given amount of money from the base currency type to a target type. 
     * @param target java.util.Currency instance representing the target type to convert to.
     * @param amount amount of money in a specific currency
     * @return converted amount of money in the target currency
     */
    public BigDecimal convertTo(Currency target, Money amount) {
        if (!Currency.getAvailableCurrencies().contains(target)) {
            throw new RuntimeException("Unsupported target currency");
        }
        BigDecimal rate = BigDecimal.ONE;
        if (!amount.getCurrency().equals(target)) {
            rate = exchangeRateApi.getRate(amount.getCurrency(), target);
        }
        
        return amount.getAmount().multiply(rate);        
    }
    
}
