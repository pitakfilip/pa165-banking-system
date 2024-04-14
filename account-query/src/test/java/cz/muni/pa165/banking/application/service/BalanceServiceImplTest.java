package cz.muni.pa165.banking.application.service;

import cz.muni.pa165.banking.application.exception.NotFoundAccountException;
import cz.muni.pa165.banking.domain.balance.Balance;
import cz.muni.pa165.banking.domain.balance.repository.BalancesRepository;

import static org.junit.jupiter.api.Assertions.*;

import cz.muni.pa165.banking.domain.balance.repository.TransactionRepository;
import cz.muni.pa165.banking.domain.report.StatisticalReport;
import cz.muni.pa165.banking.domain.transaction.Transaction;
import cz.muni.pa165.banking.domain.transaction.TransactionType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
class BalanceServiceImplTest {
    @Mock
    private BalancesRepository balanceRepository;
    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private BalanceServiceImpl balanceService;

    @Mock
    private Balance balance;
    @Mock
    private Balance balance2;

    @Test
    void findById_balanceFound_returnsBalance() {
        // Arrange
        Balance balance = new Balance("jozo");
        Mockito.when(balanceRepository.findById("jozo")).thenReturn(Optional.of(balance));

        // Act
        Balance foundEntity = balanceService.findById("jozo");

        // Assert
        assertThat(foundEntity).isEqualTo(balance);
    }

    @Test
    void findById_personNotFound_throwsResourceNotFoundException() {
        assertThrows(NotFoundAccountException.class, () -> Optional
                .ofNullable(balanceService.findById("ol")));
    }
    @Test
    void getBalance_balanceFound_returnsNum() {
        // Arrange
        Balance balance = new Balance("jozo");
        Mockito.when(balanceRepository.findById("jozo")).thenReturn(Optional.of(balance));

        // Act
        BigDecimal foundEntity = balanceService.getBalance("jozo");

        // Assert
        assertThat(foundEntity).isEqualTo(balance.getAmount());
    }

    @Test
    void getBalance_personNotFound_throwsResourceNotFoundException() {
        assertThrows(NotFoundAccountException.class, () -> Optional
                .ofNullable(balanceService.getBalance("ol")));
    }

    @Test
    void addNewBalance_createsBalance() {
        // Arrange
        String id = "id";
        Mockito.doNothing().when(balanceRepository).addBalance(id);

        // Act
        balanceService.addNewBalance(id);

        // Assert
        Mockito.verify(balanceRepository, Mockito.times(1)).addBalance(id);
    }

    @Test
    void getTransactions_accountDoesNotExist_throwsException() {
        assertThrows(NotFoundAccountException.class, () -> Optional
                .ofNullable(balanceService
                        .getTransactions("ol", OffsetDateTime.now(), OffsetDateTime.now(),
                                BigDecimal.ZERO, BigDecimal.TEN, null)));
    }
    @Test
    void getTransactionsWithNullArguments_accountExists_callsGetTransactions() {
        // Arrange
        String id = "id";
        OffsetDateTime time = OffsetDateTime.now();
        Mockito.when(balance.getData(time, time)).thenReturn(new ArrayList<>());
        Mockito.when(balanceRepository.findById(id)).thenReturn(Optional.of(balance));

        // Act
        List<Transaction> transactions = balanceService.getTransactions(id, time, time, null, null, null);

        // Assert
        Mockito.verify(balanceRepository, Mockito.times(1)).findById(id);
        Mockito.verify(balance, Mockito.times(1)).getData(time, time);
    }
    @Test
    void getTransactionsWithNullType_accountExists_callsRightGetTransactions() {
        // Arrange
        String id = "id";
        OffsetDateTime time = OffsetDateTime.now();
        Mockito.when(balance.getData(time, time, BigDecimal.ONE, BigDecimal.ONE)).thenReturn(new ArrayList<>());
        Mockito.when(balanceRepository.findById(id)).thenReturn(Optional.of(balance));

        // Act
        List<Transaction> transactions = balanceService.getTransactions(id, time, time, BigDecimal.ONE, BigDecimal.ONE, null);

        // Assert
        Mockito.verify(balanceRepository, Mockito.times(1)).findById(id);
        Mockito.verify(balance, Mockito.times(1)).getData(time, time, BigDecimal.ONE, BigDecimal.ONE);
    }

    @Test
    void getTransactions_accountExists_callsRightGetTransactions() {
        // Arrange
        String id = "id";
        OffsetDateTime time = OffsetDateTime.now();
        Mockito.when(balance.getData(time, time, BigDecimal.ONE, BigDecimal.ONE, TransactionType.CREDIT))
                .thenReturn(new ArrayList<>());
        Mockito.when(balanceRepository.findById(id)).thenReturn(Optional.of(balance));

        // Act
        List<Transaction> transactions = balanceService.getTransactions(id, time, time, BigDecimal.ONE,
                BigDecimal.ONE, TransactionType.CREDIT);

        // Assert
        Mockito.verify(balanceRepository, Mockito.times(1)).findById(id);
        Mockito.verify(balance, Mockito.times(1)).getData(time, time, BigDecimal.ONE,
                BigDecimal.ONE, TransactionType.CREDIT);
    }
    @Test
    void addToBalance_accountExists_callsAddTransaction() {
        // Arrange
        String id = "id";
        UUID uuid = new UUID(2, 2);
        OffsetDateTime time = OffsetDateTime.now();
        Mockito.when(balance.addTransaction(BigDecimal.TEN, TransactionType.CREDIT, uuid)).thenReturn(new Transaction());
        Mockito.when(balanceRepository.findById(id)).thenReturn(Optional.of(balance));

        // Act
        balanceService.addToBalance(id, BigDecimal.TEN, uuid, TransactionType.CREDIT);

        // Assert
        Mockito.verify(balanceRepository, Mockito.times(1)).findById(id);
        Mockito.verify(balance, Mockito.times(1))
                .addTransaction(BigDecimal.TEN, TransactionType.CREDIT, uuid);
    }

    @Test
    void getReport_accountExists_callsRightGetReport() {
        // Arrange
        String id = "id";
        OffsetDateTime time = OffsetDateTime.now();
        UUID uuid = new UUID(2, 2);
        Mockito.when(balance.getReport( time, time )).thenReturn(new StatisticalReport(new ArrayList<>()));
        Mockito.when(balanceRepository.findById(id)).thenReturn(Optional.of(balance));

        // Act
        balanceService.getReport(id, time, time);

        // Assert
        Mockito.verify(balanceRepository, Mockito.times(1)).findById(id);
        Mockito.verify(balance, Mockito.times(1))
                .getReport(time, time);
    }

    @Test
    void getAllTransactions_callsGetTransactionsOnAllAccounts() {
        // Arrange
        String id = "id";
        String id2 = "id2";
        OffsetDateTime time = OffsetDateTime.now();
        UUID uuid = new UUID(2, 2);
        Mockito.when(balance.getData(time, time)).thenReturn(new ArrayList<>());
        Mockito.when(balance2.getData(time, time)).thenReturn(new ArrayList<>());
        Mockito.when(balanceRepository.getAllIds()).thenReturn(List.of(id, id2));
        Mockito.when(balanceRepository.findById(id)).thenReturn(Optional.of(balance));
        Mockito.when(balanceRepository.findById(id2)).thenReturn(Optional.of(balance2));

        // Act
        balanceService.getAllTransactions(time, time, null, null, null);

        // Assert
        Mockito.verify(balance, Mockito.times(1)).getData(time, time);
        Mockito.verify(balance2, Mockito.times(1)).getData(time, time);
    }
}