package cz.muni.pa165.banking.application.proxy;

import cz.muni.pa165.banking.account.query.SystemServiceApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(url = "${banking.apps.query.url}", name = "SystemBalanceApi")
public interface SystemServiceProxy extends SystemServiceApi {
}
