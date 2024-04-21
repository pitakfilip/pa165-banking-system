package cz.muni.pa165.banking.application.configuration;

import cz.muni.pa165.banking.domain.money.exchange.ExchangeRateService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Currency;
import java.util.List;

@Component
public class ExchangeRateInitializer {
    
    private final Logger logger = LoggerFactory.getLogger(ExchangeRateInitializer.class);
    
    private final ExchangeRateService exchangeRateService;

    @Value("#{'${banking.apps.rates.initial.currencies}'.split(',')}")
    private List<String> currencies;
    
    public ExchangeRateInitializer(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }
    
    @PostConstruct
    void initialize() {
        for (String currency : currencies) {
            try {
                // ignore result, just call service to trigger mechanism to cache data
                exchangeRateService.getRate(Currency.getInstance(currency), Currency.getInstance(currency));
            } catch (Exception e) {
                logger.warn(String.format("Initializing exchange rates for %s failed. API might be unavailable!", currency));
            }
        }
    }
    
}
