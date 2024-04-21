package cz.muni.pa165.banking.application.proxy.rate;

import cz.muni.pa165.banking.exception.UnexpectedValueException;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

public class ExchangeRateResponseProcessor {
    
    public static Map<Currency, BigDecimal> process(Map<String, Object> response) {
        if (!response.get("result").equals("success")) {
            throw new UnexpectedValueException("Exchange rate API unavailable");
        }

        Map<String, Object> rates = (Map<String, Object>) response.get("conversion_rates");
        
        Map<Currency, BigDecimal> result = new HashMap<>();
        for (String currencyCode : rates.keySet()) {
            Currency currency;
            try {
                currency = Currency.getInstance(currencyCode);
            } catch (Exception e) {
                continue;
            }
            Object value = rates.get(currencyCode);
            
            BigDecimal rate;
            if (value instanceof Integer val) {
                rate = BigDecimal.valueOf(val);
            } else if (value instanceof Double val) {
                rate = BigDecimal.valueOf(val);
            } else {
                String strVal = value.toString();
                double val = Double.parseDouble(strVal);
                rate = BigDecimal.valueOf(val);
            }
            
            result.put(currency, rate);
        }
        
        return result;
    }
    
}
