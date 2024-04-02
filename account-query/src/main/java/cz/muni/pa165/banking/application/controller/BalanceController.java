package cz.muni.pa165.banking.application.controller;

import cz.muni.pa165.banking.account.query.CustomerServiceApi;
import cz.muni.pa165.banking.account.query.SystemServiceApi;
import cz.muni.pa165.banking.account.query.dto.Balance;
import cz.muni.pa165.banking.account.query.dto.Transaction;
import cz.muni.pa165.banking.application.facade.BalanceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Martin Mojzis
 */
@RestController
@RequestMapping("/balance")
public class BalanceController implements CustomerServiceApi, SystemServiceApi {
    private BalanceFacade balanceFacade;
    @Autowired
    public void BalanceApi(BalanceFacade balanceFacade) {
        this.balanceFacade = balanceFacade;
    }
    @Override
    public ResponseEntity<BigDecimal> getBalance(String id) {

        return null;
    }

    @Override
    public ResponseEntity<List<Transaction>> getTransactions(String id, LocalDate beginning, LocalDate end, BigDecimal minAmount, BigDecimal maxAmount, String type) {
        return null;
    }

    @Override
    public ResponseEntity<Void> addTransactionToBalance(String id, BigDecimal amount, String processId, String type) {
        return null;
    }

    @Override
    public ResponseEntity<Void> createBalance(String id) {
        balanceFacade.createNewBalance(id);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    //TODO generate from openapi, other methods - add to balance, refund, statistics, view account

//
//    @PutMapping("/new")
//    public ResponseEntity createNew(@RequestBody String customerId) {
//        try {
//            balanceFacade.createNewBalance(customerId);
//        }
//        catch(RuntimeException e){
//            return new ResponseEntity(HttpStatusCode.valueOf(400));
//        }
//        return new ResponseEntity(HttpStatusCode.valueOf(200));
//    }
}
