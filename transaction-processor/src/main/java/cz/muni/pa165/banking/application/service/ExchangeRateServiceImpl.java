package cz.muni.pa165.banking.application.service;

import cz.muni.pa165.banking.application.proxy.rate.ExchangeRateResponseProcessor;
import cz.muni.pa165.banking.application.proxy.rate.ExchangeRatesApi;
import cz.muni.pa165.banking.domain.money.exchange.ExchangeRateService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {
    
    private final ExchangeRatesApi exchangeRatesApi;
    
    Map<Currency, Map<Currency, BigDecimal>> exchangeRates = new ConcurrentHashMap<>();

    public ExchangeRateServiceImpl(ExchangeRatesApi exchangeRatesApi) {
        this.exchangeRatesApi = exchangeRatesApi;
    }

    @Override
    public BigDecimal getRate(Currency base, Currency target) {
        if (!exchangeRates.containsKey(base)) {
            Map<String, Object> response = exchangeRatesApi.getRatesOfCurrency(base.getCurrencyCode());
            exchangeRates.put(base, ExchangeRateResponseProcessor.process(response));
        }
        
        return exchangeRates.get(base).get(target);
    }

}
