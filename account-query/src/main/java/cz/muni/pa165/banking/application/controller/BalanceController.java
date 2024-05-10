package cz.muni.pa165.banking.application.controller;

import cz.muni.pa165.banking.account.query.CustomerServiceApi;
import cz.muni.pa165.banking.account.query.SystemServiceApi;
import cz.muni.pa165.banking.account.query.dto.Transaction;
import cz.muni.pa165.banking.account.query.dto.TransactionType;
import cz.muni.pa165.banking.application.facade.BalanceFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * @author Martin Mojzis
 */
@RestController
public class BalanceController implements CustomerServiceApi, SystemServiceApi {

    private final BalanceFacade balanceFacade;

    public BalanceController(BalanceFacade balanceFacade) {
        this.balanceFacade = balanceFacade;
    }

    @Override
    @PreAuthorize("hasAnyAuthority('SCOPE_test_1', 'SCOPE_test_2', 'SCOPE_test_3')")
    public ResponseEntity<BigDecimal> getBalance(String id) {
        BigDecimal result = balanceFacade.getBalance(id);
        return ResponseEntity.ok(result);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('SCOPE_test_1', 'SCOPE_test_2', 'SCOPE_test_3')")
    public ResponseEntity<List<Transaction>> getTransactions(String id, LocalDate beginning, LocalDate end, BigDecimal minAmount, BigDecimal maxAmount, TransactionType type) {
        List<Transaction> toReturn = balanceFacade.getTransactions(id, beginning, end, minAmount, maxAmount, type);
        return ResponseEntity.ok(toReturn);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('SCOPE_test_1', 'SCOPE_test_2', 'SCOPE_test_3')")
    public ResponseEntity<Void> addTransactionToBalance(String id, BigDecimal amount, UUID processId, TransactionType type) {
        balanceFacade.addToBalance(id, processId, amount, type);
        return ResponseEntity.ok().build();
    }

    @Override
    @PreAuthorize("hasAnyAuthority('SCOPE_test_2', 'SCOPE_test_3')")
    public ResponseEntity<Void> createBalance(String id) {
        balanceFacade.createNewBalance(id);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('SCOPE_test_2', 'SCOPE_test_3')")
    public ResponseEntity<Void> deleteBalance(String id) {
        balanceFacade.deleteBalance(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
