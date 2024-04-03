package cz.muni.pa165.banking.application.controller;

import cz.muni.pa165.banking.account.query.CustomerServiceApi;
import cz.muni.pa165.banking.account.query.SystemServiceApi;
import cz.muni.pa165.banking.account.query.dto.Transaction;
import cz.muni.pa165.banking.account.query.dto.TransactionType;
import cz.muni.pa165.banking.application.facade.BalanceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Martin Mojzis
 */
@RestController
public class BalanceController implements CustomerServiceApi, SystemServiceApi {
    private final BalanceFacade balanceFacade;
    @Autowired
    public BalanceController(BalanceFacade balanceFacade) {
        this.balanceFacade = balanceFacade;
    }

    @Override
    public ResponseEntity<BigDecimal> getBalance(String id) {
        BigDecimal result = balanceFacade.getBalance(id);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<List<Transaction>> getTransactions(String id, LocalDate beginning, LocalDate end,
                                                             BigDecimal minAmount, BigDecimal maxAmount, TransactionType type) {
        List<Transaction> toReturn = balanceFacade.getTransactions(id, beginning, end, minAmount, maxAmount, type);
        return ResponseEntity.ok(toReturn);
    }

    @Override
    public ResponseEntity<Void> addTransactionToBalance(String id, BigDecimal amount, String processId, TransactionType type) {
        balanceFacade.addToBalance(id, processId, amount, type);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> createBalance(String id) {
        balanceFacade.createNewBalance(id);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }
}
