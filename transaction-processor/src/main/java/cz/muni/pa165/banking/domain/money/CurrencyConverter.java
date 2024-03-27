package cz.muni.pa165.banking.domain.money;

import cz.muni.pa165.banking.domain.money.service.ExchangeRateService;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CurrencyConverter {
    
    private final Currency BASE_CURRENCY;

    private final ExchangeRateService exchangeRateApi;
    
    private Map<Currency, BigDecimal> rates;
    
    public CurrencyConverter(Currency baseCurrency, ExchangeRateService exchangeRateApi) {
        BASE_CURRENCY = baseCurrency;
        this.exchangeRateApi = exchangeRateApi;
        loadTable();
    }
    
    public CurrencyConverter(ExchangeRateService exchangeRateApi) {
        this.exchangeRateApi = exchangeRateApi;
        BASE_CURRENCY = Currency.getInstance("CZK");
        loadTable();
    }

    private void loadTable() {
        rates = new HashMap<>();
        Set<Currency> supportedCurrencies = Currency.getAvailableCurrencies();
        for (Currency currency : supportedCurrencies) {
            if (!currency.equals(BASE_CURRENCY)) {
                BigDecimal rate = exchangeRateApi.getRate(BASE_CURRENCY, currency);
                rates.put(currency, rate);
            }
        }
    }

    /**
     * Converts a given amount of money from the base currency type to a target type. 
     * The base type is either 'CZK' by default or may be defined when instantiating {@link CurrencyConverter#CurrencyConverter(Currency, ExchangeRateService)}
     * @param target java.util.Currency instance representing the target type to convert to.
     * @param amount 
     * @return BigDecimal instance representing the converted amount of money into the target currency
     */
    public BigDecimal convertTo(Currency target, BigDecimal amount) {
        BigDecimal rate = rates.get(target);
        if (rate == null) {
            throw new RuntimeException("Unsupported currency");
        }
        return amount.multiply(rate);        
    }
    
}
