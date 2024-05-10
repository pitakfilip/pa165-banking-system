package cz.muni.pa165.banking.application.proxy;

import cz.muni.pa165.banking.account.management.AccountApi;
import cz.muni.pa165.banking.feign.FeignAuthInterceptor;
import cz.muni.pa165.banking.feign.FeignClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(url = "${banking.apps.management.url}", name = "AccountApi", configuration = FeignClientConfiguration.class)
public interface AccountApiProxy extends AccountApi {
}
