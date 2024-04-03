package cz.muni.pa165.banking.application.service;

import cz.muni.pa165.banking.domain.money.exchange.ExchangeRateService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Currency;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {
    
//    private final ExchangeRatesApi proxy;

//    public ExchangeRateServiceImpl(ExchangeRatesApi proxy) {
//        this.proxy = proxy;
//    }

    @Override
    public BigDecimal getRate(Currency base, Currency target) {
        return null;
    }
}
