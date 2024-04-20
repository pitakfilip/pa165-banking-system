package cz.muni.pa165.banking.application.facade;

import cz.muni.pa165.banking.account.management.dto.NewAccountDto;
import cz.muni.pa165.banking.account.management.dto.AccountDto;
import cz.muni.pa165.banking.account.management.dto.ScheduledPaymentDto;
import cz.muni.pa165.banking.account.management.dto.ScheduledPaymentsDto;
import cz.muni.pa165.banking.application.mapper.DtoMapper;
import cz.muni.pa165.banking.application.service.AccountService;
import cz.muni.pa165.banking.domain.account.Account;
import cz.muni.pa165.banking.domain.scheduled.ScheduledPayment;
import cz.muni.pa165.banking.domain.scheduled.ScheduledPaymentProjection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountFacadeTest {
    
    @Mock
    private AccountService accountService;

    @Mock
    private DtoMapper mapper;

    @InjectMocks
    private AccountFacade accountFacade;

    @Test
    void createAccount_ValidRequest_ReturnsAccountDto() {
        // Arrange
        NewAccountDto newAccountDto = new NewAccountDto();
        AccountDto accountDto = new AccountDto();
        when(mapper.map(any(Account.class))).thenReturn(accountDto);
        when(mapper.map(newAccountDto)).thenReturn(new Account());
        when(accountService.createAccount(any())).thenReturn(new Account());

        // Act
        AccountDto result = accountFacade.createAccount(newAccountDto);

        // Assert
        assertEquals(accountDto, result);
        verify(accountService).createAccount(any());
        verify(mapper).map(any(Account.class));
    }

    @Test
    void getAccount_ValidAccountNumber_ReturnsAccountDto() throws Exception {
        // Arrange
        Long accountId = 123456789L;
        AccountDto accountDto = new AccountDto();
        when(mapper.map(any(Account.class))).thenReturn(accountDto);
        when(accountService.findById(accountId)).thenReturn(new Account());

        // Act
        AccountDto result = accountFacade.findById(accountId);

        // Assert
        assertEquals(accountDto, result);
        verify(accountService).findById(accountId);
        verify(mapper).map(any(Account.class));
    }

    @Test
    void getAccount_InvalidAccountNumber_ReturnsNull() throws Exception {
        // Arrange
        Long invalidId = 123456789L;
        when(accountService.findById(invalidId)).thenReturn(null);

        // Act & Assert
        assertNull(accountFacade.findById(invalidId));
    }

    @Test
    void schedulePayment_ValidRequest_ReturnsScheduledPaymentDto() {
        // Arrange
        ScheduledPaymentDto scheduledPaymentDto = new ScheduledPaymentDto();
        when(mapper.mapScheduledPayment(any(ScheduledPayment.class), any(), any())).thenReturn(scheduledPaymentDto);
        when(accountService.createNewScheduledPayment(any(), any(), any(), any(), any())).thenReturn(new ScheduledPayment());

        // Act
        ScheduledPaymentDto result = accountFacade.schedulePayment(scheduledPaymentDto);

        // Assert
        assertEquals(scheduledPaymentDto, result);
        verify(accountService).createNewScheduledPayment(any(), any(), any(), any(), any());
    }

    @Test
    void getScheduledPaymentsOfAccount_ValidAccountNumber_ReturnsPayments() throws Exception {
        // Arrange
        String validAccountNumber = "123456789";
        ScheduledPaymentsDto scheduledPaymentsDto = new ScheduledPaymentsDto();
        List<ScheduledPaymentProjection> scheduledPaymentsList = new ArrayList<>();
        when(accountService.findScheduledPaymentsByAccount(any(String.class))).thenReturn(scheduledPaymentsList);
        when(mapper.map(scheduledPaymentsList)).thenReturn(scheduledPaymentsDto);

        // Act
        ScheduledPaymentsDto result = accountFacade.findScheduledPaymentsByNumber(validAccountNumber);

        // Assert
        assertNotNull(result);
        verify(accountService).findScheduledPaymentsByAccount(eq(validAccountNumber));
        verify(mapper).map(scheduledPaymentsList);
    }

}
