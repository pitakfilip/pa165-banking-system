package cz.muni.pa165.banking.domain.money;

import cz.muni.pa165.banking.domain.money.exchange.ExchangeRateService;
import cz.muni.pa165.banking.exception.UnsupportedDataTypeException;

import java.math.BigDecimal;
import java.util.Currency;

public class CurrencyConverter {
    
    private final ExchangeRateService exchangeRateApi;
    
    public CurrencyConverter(ExchangeRateService exchangeRateApi) {
        this.exchangeRateApi = exchangeRateApi;
    }

    /**
     * Converts a given amount of money from the source currency type to a target type. 
     */
    public BigDecimal convertTo(Currency source, Currency target, BigDecimal amount) {
        if (!Currency.getAvailableCurrencies().contains(source)) {
            throw new UnsupportedDataTypeException("Unsupported source currency");
        }
        if (!Currency.getAvailableCurrencies().contains(target)) {
            throw new UnsupportedDataTypeException("Unsupported target currency");
        }
        
        BigDecimal rate = BigDecimal.ONE;
        if (!source.equals(target)) {
            rate = exchangeRateApi.getRate(source, target);
        }
        
        return amount.multiply(rate);        
    }
    
}
