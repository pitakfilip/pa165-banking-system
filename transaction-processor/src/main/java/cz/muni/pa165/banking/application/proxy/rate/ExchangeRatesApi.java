package cz.muni.pa165.banking.application.proxy.rate;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

// https://app.exchangerate-api.com
@FeignClient(name = "ExchangeRateApi", url = "${banking.apps.rates.url}")
public interface ExchangeRatesApi {
    
    @GetMapping("/latest/{currency}")
    Map<String, Object> getRatesOfCurrency(@RequestParam String currency);
    
}
