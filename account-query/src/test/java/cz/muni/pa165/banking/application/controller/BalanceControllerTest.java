package cz.muni.pa165.banking.application.controller;

import cz.muni.pa165.banking.account.query.dto.Transaction;
import cz.muni.pa165.banking.account.query.dto.TransactionType;
import cz.muni.pa165.banking.application.facade.BalanceFacade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class BalanceControllerTest {
    @Mock
    private BalanceFacade balanceFacade;

    @InjectMocks
    private BalanceController balanceController;

    @Test
    void getBalance_personFound_returnsbalance() {
        // Arrange
        String id = "id";
        Mockito.when(balanceFacade.getBalance(id)).thenReturn(BigDecimal.ONE);

        // Act
        ResponseEntity<BigDecimal> response = balanceController.getBalance(id);

        // Assert
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isEqualTo(BigDecimal.ONE);
    }

    @Test
    void getTransactions_personFound_returnsTransactions() {
        // Arrange
        String id = "id";
        LocalDate date = LocalDate.now();
        List<Transaction> list = new ArrayList<>();
        Mockito.when(balanceFacade.getTransactions(id, date, date, BigDecimal.ONE, BigDecimal.TEN, TransactionType.CREDIT))
                .thenReturn(list);

        // Act
        ResponseEntity<List<Transaction>> response = balanceController
                .getTransactions(id, date, date, BigDecimal.ONE, BigDecimal.TEN, TransactionType.CREDIT);

        // Assert
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isEqualTo(list);
    }

    @Test
    void addTransactionToBalance_personFound_callsAddTransaction() {
        // Arrange
        String id = "id";
        UUID uuid = new UUID(2, 2);
        Mockito.doNothing().when(balanceFacade).addToBalance(id, uuid, BigDecimal.ONE, TransactionType.CREDIT);


        // Act
        ResponseEntity<Void> response = balanceController.addTransactionToBalance(id, BigDecimal.ONE, uuid, TransactionType.CREDIT);

        // Assert
        Mockito.verify(balanceFacade, Mockito.times(1))
                .addToBalance(id, uuid, BigDecimal.ONE, TransactionType.CREDIT);
        assertThat(response.getBody()).isNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void createBalance_createsBalance() {
        // Arrange
        String id = "id";
        Mockito.doNothing().when(balanceFacade).createNewBalance(id);

        // Act
        ResponseEntity<Void> response = balanceController.createBalance(id);

        // Assert
        Mockito.verify(balanceFacade, Mockito.times(1))
                .createNewBalance(id);
        assertThat(response.getBody()).isNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }
}