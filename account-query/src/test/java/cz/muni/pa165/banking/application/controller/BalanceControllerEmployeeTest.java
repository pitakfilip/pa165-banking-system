package cz.muni.pa165.banking.application.controller;

import cz.muni.pa165.banking.account.query.dto.Transaction;
import cz.muni.pa165.banking.account.query.dto.TransactionType;
import cz.muni.pa165.banking.account.query.dto.TransactionsReport;
import cz.muni.pa165.banking.application.facade.BalanceFacade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
public class BalanceControllerEmployeeTest {
    @Mock
    private BalanceFacade balanceFacade;

    @InjectMocks
    private BalanceControllerEmployee balanceControllerEmployee;

    @Test
    void getTransactions_personFound_returnsTransactions() {
        // Arrange
        String id = "id";
        LocalDate date = LocalDate.now();
        List<Transaction> list = new ArrayList<>();
        Mockito.when(balanceFacade.getAllTransactions(date, date, BigDecimal.ONE, BigDecimal.TEN, TransactionType.CREDIT))
                .thenReturn(list);

        // Act
        ResponseEntity<List<Transaction>> response = balanceControllerEmployee
                .getAllTransactions(date, date, BigDecimal.ONE, BigDecimal.TEN, TransactionType.CREDIT);

        // Assert
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isEqualTo(list);
    }

    @Test
    void getReport_personFound_returnsReport() {
        // Arrange
        String id = "id";
        LocalDate date = LocalDate.now();
        TransactionsReport report = new TransactionsReport();
        Mockito.when(balanceFacade.getReport(id, date, date))
                .thenReturn(report);

        // Act
        ResponseEntity<TransactionsReport> response = balanceControllerEmployee
                .createReport(id, date, date);

        // Assert
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isEqualTo(report);
    }
}
