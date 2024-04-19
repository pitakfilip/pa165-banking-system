package cz.muni.pa165.banking.application.facade;

import cz.muni.pa165.banking.account.management.dto.NewAccountDto;
import cz.muni.pa165.banking.account.management.dto.AccountDto;
import cz.muni.pa165.banking.account.management.dto.ScheduledPaymentDto;
import cz.muni.pa165.banking.account.management.dto.ScheduledPaymentsDto;
import cz.muni.pa165.banking.application.mapper.DtoMapper;
import cz.muni.pa165.banking.application.service.AccountService;
import cz.muni.pa165.banking.domain.account.Account;
import cz.muni.pa165.banking.domain.scheduled.ScheduledPayment;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
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
        when(mapper.map(any(ScheduledPayment.class))).thenReturn(scheduledPaymentDto);
        when(accountService.schedulePayment(any())).thenReturn(new ScheduledPayment());

        // Act
        ScheduledPaymentDto result = accountFacade.schedulePayment(scheduledPaymentDto);

        // Assert
        assertEquals(scheduledPaymentDto, result);
        verify(accountService).schedulePayment(any());
        verify(mapper).map(any(ScheduledPayment.class));
    }

    @Test
    void schedulePayment_InvalidRequest_ReturnsNull() {
        // Arrange
        ScheduledPaymentDto scheduledPaymentDto = new ScheduledPaymentDto();
        when(accountService.schedulePayment(any())).thenReturn(null);

        // Act
        ScheduledPaymentDto result = accountFacade.schedulePayment(scheduledPaymentDto);

        // Assert
        assertNull(result);
        verify(accountService).schedulePayment(any());
    }

    @Test
    void getScheduledPaymentsOfAccount_ValidAccountNumber_ReturnsPayments() throws Exception {
        // Arrange
        String validAccountNumber = "123456789";
        ScheduledPaymentsDto scheduledPaymentsDto = new ScheduledPaymentsDto();
        List<ScheduledPayment> scheduledPaymentsList = new ArrayList<>();
        Account account = new Account();
        account.setId(1L);
        when(accountService.findByNumber(validAccountNumber)).thenReturn(account);
        when(accountService.findScheduledPaymentsById(any(Long.class))).thenReturn(scheduledPaymentsList);
        when(mapper.map(scheduledPaymentsList)).thenReturn(scheduledPaymentsDto);

        // Act
        ScheduledPaymentsDto result = accountFacade.findScheduledPaymentsByNumber(validAccountNumber);

        // Assert
        assertNotNull(result);
        verify(accountService).findScheduledPaymentsById(eq(1L));
        verify(mapper).map(scheduledPaymentsList);
    }



    @Test
    void getScheduledPaymentsOfAccount_InvalidAccountNumber_ThrowsException() throws Exception {
        // Arrange
        String invalidAccountNumber = "invalidAccountNumber";
        when(accountService.findByNumber(invalidAccountNumber)).thenReturn(null);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> accountFacade.findScheduledPaymentsByNumber(invalidAccountNumber));
    }
}
