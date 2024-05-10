package cz.muni.pa165.banking.application.controller;

import cz.muni.pa165.banking.account.query.EmployeeServiceApi;
import cz.muni.pa165.banking.account.query.dto.Transaction;
import cz.muni.pa165.banking.account.query.dto.TransactionType;
import cz.muni.pa165.banking.account.query.dto.TransactionsReport;
import cz.muni.pa165.banking.application.facade.BalanceFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Martin Mojzis
 */
@RestController
public class BalanceControllerEmployee implements EmployeeServiceApi {

    private final BalanceFacade balanceFacade;

    public BalanceControllerEmployee(BalanceFacade balanceFacade) {
        this.balanceFacade = balanceFacade;
    }

    @Override
    @PreAuthorize("hasAnyAuthority('SCOPE_test_2', 'SCOPE_test_3')")
    public ResponseEntity<TransactionsReport> createReport(String id, LocalDate beginning, LocalDate end) {
        TransactionsReport result = balanceFacade.getReport(id, beginning, end);
        return ResponseEntity.ok(result);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('SCOPE_test_2', 'SCOPE_test_3')")
    public ResponseEntity<List<Transaction>> getAllTransactions(LocalDate beginning, LocalDate end,
                                                                BigDecimal minAmount, BigDecimal maxAmount, TransactionType type) {
        List<Transaction> result = balanceFacade.getAllTransactions(beginning, end, minAmount, maxAmount, type);
        return ResponseEntity.ok(result);
    }
}
