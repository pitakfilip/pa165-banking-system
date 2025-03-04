package cz.muni.pa165.banking.domain.money.exchange;

import java.math.BigDecimal;
import java.util.Currency;

public interface ExchangeRateService {
    
    BigDecimal getRate(Currency base, Currency target);
    
}
