package cz.muni.pa165.banking.application.proxy;

import cz.muni.pa165.banking.account.query.CustomerServiceApi;
import cz.muni.pa165.banking.feign.FeignClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(url = "${banking.apps.query.url}", name = "CustomerServiceApi", configuration = FeignClientConfiguration.class)
public interface CustomerServiceApiProxy extends CustomerServiceApi {
}
