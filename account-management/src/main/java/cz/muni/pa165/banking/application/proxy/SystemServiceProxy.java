package cz.muni.pa165.banking.application.proxy;

import cz.muni.pa165.banking.account.query.SystemServiceApi;
import cz.muni.pa165.banking.feign.FeignClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(url = "${banking.apps.query.url}", name = "SystemBalanceApi", configuration = FeignClientConfiguration.class)
public interface SystemServiceProxy extends SystemServiceApi {
}
