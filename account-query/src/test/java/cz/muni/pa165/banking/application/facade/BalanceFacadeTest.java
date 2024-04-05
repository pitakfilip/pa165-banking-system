package cz.muni.pa165.banking.application.facade;

import static cz.muni.pa165.banking.domain.transaction.TransactionType.CREDIT;
import static org.junit.jupiter.api.Assertions.*;

import cz.muni.pa165.banking.application.mapper.BalanceMapper;
import cz.muni.pa165.banking.application.service.BalanceServiceImpl;
import cz.muni.pa165.banking.domain.balance.Balance;
import cz.muni.pa165.banking.domain.report.StatisticalReport;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@ExtendWith(MockitoExtension.class)
class BalanceFacadeTest {
    @Mock
    BalanceServiceImpl balanceService;
    @Mock
    BalanceMapper balanceMapper;
    @InjectMocks
    BalanceFacade balanceFacade;

    @Test
    void getBalance_balanceExists_returnsBalance() {
        // Arrange
        Mockito.when(balanceService.getBalance("id1")).thenReturn(new Balance("id1").getAmount());

        // Act
        BigDecimal num = balanceFacade.getBalance("id1");

        // Assert
        assertThat(num).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    void createNewBalance_createsBalance() {
        // Arrange
        Mockito.doNothing().when(balanceService).addNewBalance("id1");

        // Act
        balanceFacade.createNewBalance("id1");

        // Assert
        Mockito.verify(balanceService, Mockito.times(1)).addNewBalance("id1");
    }

    @Test
    void addToBalance_balanceExists_addToBalanceCalled() {
        // Arrange
        UUID id = new UUID(2, 2);
        Mockito.doNothing().when(balanceService).addToBalance("id1", BigDecimal.TEN, id, CREDIT);
        Mockito.when(balanceMapper.mapTypeOut(cz.muni.pa165.banking.account.query.dto.TransactionType.CREDIT)).thenReturn(CREDIT);

        // Act
        balanceFacade.addToBalance("id1", id, BigDecimal.TEN, cz.muni.pa165.banking.account.query.dto.TransactionType.CREDIT);

        // Assert
        Mockito.verify(balanceService, Mockito.times(1))
                .addToBalance("id1", BigDecimal.TEN, id, CREDIT);
    }

    @Test
    void getTransactions_accountExist_callGetTransactions() {
        //Arrange
        UUID id = new UUID(2, 2);
        Mockito.when(balanceService.getTransactions("id1", OffsetDateTime.of(LocalDate.now(),
                        LocalTime.MIDNIGHT, ZoneOffset.UTC), OffsetDateTime.of(LocalDate.now(),
                        LocalTime.MIDNIGHT, ZoneOffset.UTC),
                BigDecimal.ZERO, BigDecimal.TEN, null)).thenReturn(
                List.of(new cz.muni.pa165.banking.domain.transaction.Transaction
                        (CREDIT, BigDecimal.TEN, OffsetDateTime.now(), id)));
        // Act
        balanceFacade.getTransactions("id1", LocalDate.now(), LocalDate.now(),
                BigDecimal.ZERO, BigDecimal.TEN, null);

        //Assert
        Mockito.verify(balanceService, Mockito.times(1))
                .getTransactions("id1", OffsetDateTime.of(LocalDate.now(),
                                LocalTime.MIDNIGHT, ZoneOffset.UTC), OffsetDateTime.of(LocalDate.now(),
                                LocalTime.MIDNIGHT, ZoneOffset.UTC),
                        BigDecimal.ZERO, BigDecimal.TEN, null);
    }

    @Test
    void getReport_accountExist_callGetReport() {
        //Arrange
        Mockito.when(balanceService.getReport("id1", OffsetDateTime.of(LocalDate.now(),
                LocalTime.MIDNIGHT, ZoneOffset.UTC), OffsetDateTime.of(LocalDate.now(),
                LocalTime.MIDNIGHT, ZoneOffset.UTC))).thenReturn(new StatisticalReport(new ArrayList<>()));
        // Act
        balanceFacade.getReport("id1", LocalDate.now(), LocalDate.now());

        //Assert
        Mockito.verify(balanceService, Mockito.times(1))
                .getReport("id1", OffsetDateTime.of(LocalDate.now(),
                        LocalTime.MIDNIGHT, ZoneOffset.UTC), OffsetDateTime.of(LocalDate.now(),
                        LocalTime.MIDNIGHT, ZoneOffset.UTC));
    }

    @Test
    void getAllTransactions_callGetAllTransactions() {
        //Arrange
        Mockito.when(balanceService.getAllTransactions(OffsetDateTime.of(LocalDate.now(),
                        LocalTime.MIDNIGHT, ZoneOffset.UTC), OffsetDateTime.of(LocalDate.now(),
                        LocalTime.MIDNIGHT, ZoneOffset.UTC),
                BigDecimal.ZERO, BigDecimal.TEN, null)).thenReturn(
                List.of(new cz.muni.pa165.banking.domain.transaction.Transaction
                        (CREDIT, BigDecimal.TEN, OffsetDateTime.now(), new UUID(2, 2))));
        // Act
        balanceFacade.getAllTransactions(LocalDate.now(), LocalDate.now(),
                BigDecimal.ZERO, BigDecimal.TEN, null);

        //Assert
        Mockito.verify(balanceService, Mockito.times(1))
                .getAllTransactions(OffsetDateTime.of(LocalDate.now(),
                                LocalTime.MIDNIGHT, ZoneOffset.UTC), OffsetDateTime.of(LocalDate.now(),
                                LocalTime.MIDNIGHT, ZoneOffset.UTC),
                        BigDecimal.ZERO, BigDecimal.TEN, null);
    }
}