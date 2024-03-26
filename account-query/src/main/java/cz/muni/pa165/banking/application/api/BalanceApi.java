package cz.muni.pa165.banking.application.api;

import cz.muni.pa165.banking.application.facade.BalanceFacade;
import cz.muni.pa165.banking.application.service.BalanceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Martin Mojzis
 */
@RestController
@RequestMapping("/balance")
public class BalanceApi {
    //TODO generate from openapi
    private final BalanceFacade balanceFacade;

    @Autowired
    public BalanceApi(BalanceFacade balanceFacade) {
        this.balanceFacade = balanceFacade;
    }

    @PutMapping("/new")
    public ResponseEntity createNew(@RequestBody int customerId) {
        try {
            balanceFacade.createNewBalance(customerId);
        }
        catch(RuntimeException e){
            return new ResponseEntity(HttpStatusCode.valueOf(400));
        }
        return new ResponseEntity(HttpStatusCode.valueOf(200));
    }
}
