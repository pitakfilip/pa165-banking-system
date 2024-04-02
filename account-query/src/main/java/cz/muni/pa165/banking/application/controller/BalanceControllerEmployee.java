package cz.muni.pa165.banking.application.controller;

import cz.muni.pa165.banking.account.query.EmployeeServiceApi;
import cz.muni.pa165.banking.account.query.dto.Transaction;
import cz.muni.pa165.banking.account.query.dto.TransactionsReport;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Martin Mojzis
 */
public class BalanceControllerEmployee implements EmployeeServiceApi {
    @Override
    public ResponseEntity<TransactionsReport> createReport(String id, LocalDate beginning, LocalDate end) {
        return null;
    }

    @Override
    public ResponseEntity<List<Transaction>> getAllTransactions(LocalDate beginning, LocalDate end, BigDecimal minAmount, BigDecimal maxAmount, String type) {
        return null;
    }
}
