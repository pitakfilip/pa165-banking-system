package cz.muni.pa165.banking.application.proxy;

import cz.muni.pa165.banking.account.management.AccountApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(url = "${banking.apps.management.url}", name = "AccountApi")
public interface AccountApiProxy extends AccountApi {
}
