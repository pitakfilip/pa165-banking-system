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
    void getAccount_ValidAccountNumber_ReturnsAccountDto() {
        // Arrange
        String accountNumber = "123456789";
        AccountDto accountDto = new AccountDto();
        when(mapper.map(any(Account.class))).thenReturn(accountDto);
        when(accountService.getAccountByNumber(accountNumber)).thenReturn(new Account());

        // Act
        AccountDto result = accountFacade.getAccount(accountNumber);

        // Assert
        assertEquals(accountDto, result);
        verify(accountService).getAccountByNumber(accountNumber);
        verify(mapper).map(any(Account.class));
    }

    @Test
    void getAccount_InvalidAccountNumber_ReturnsNull() {
        // Arrange
        String invalidAccountNumber = "invalidAccountNumber";
        when(accountService.getAccountByNumber(invalidAccountNumber)).thenReturn(null);

        // Act & Assert
        assertNull(accountFacade.getAccount(invalidAccountNumber));
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
    void getScheduledPaymentsOfAccount_ValidAccountNumber_ReturnsPayments() {
        // Arrange
        String validAccountNumber = "123456789";
        ScheduledPaymentsDto scheduledPaymentsDto = new ScheduledPaymentsDto();
        List<ScheduledPayment> scheduledPaymentsList = new ArrayList<>();
        Account account = new Account();
        account.setId(1L);
        when(accountService.getAccountByNumber(validAccountNumber)).thenReturn(account);
        when(accountService.getScheduledPaymentsOfAccount(any(Long.class))).thenReturn(scheduledPaymentsList);
        when(mapper.map(scheduledPaymentsList)).thenReturn(scheduledPaymentsDto);

        // Act
        ScheduledPaymentsDto result = accountFacade.getScheduledPaymentsOfAccount(validAccountNumber);

        // Assert
        assertNotNull(result);
        verify(accountService).getScheduledPaymentsOfAccount(eq(1L));
        verify(mapper).map(scheduledPaymentsList);
    }


    @Test
    void getScheduledPaymentsOfAccount_InvalidAccountNumber_ThrowsException() {
        // Arrange
        String invalidAccountNumber = "invalidAccountNumber";
        when(accountService.getAccountByNumber(invalidAccountNumber)).thenReturn(null);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> accountFacade.getScheduledPaymentsOfAccount(invalidAccountNumber));
    }
}
