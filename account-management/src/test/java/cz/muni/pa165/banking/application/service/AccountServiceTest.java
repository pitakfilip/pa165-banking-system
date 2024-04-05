package cz.muni.pa165.banking.application.service;

import cz.muni.pa165.banking.domain.account.Account;
import cz.muni.pa165.banking.domain.account.repository.AccountRepository;
import cz.muni.pa165.banking.domain.scheduled.ScheduledPayment;
import cz.muni.pa165.banking.domain.scheduled.repository.ScheduledPaymentRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private ScheduledPaymentRepository scheduledPaymentRepository;

    @InjectMocks
    private AccountService accountService;

    @Test
    void createAccount_ValidAccount_ReturnsAccount() {
        // Arrange
        Account account = new Account();
        when(accountRepository.addAccount(account)).thenReturn(account);

        // Act
        Account result = accountService.createAccount(account);

        // Assert
        assertEquals(account, result);
        verify(accountRepository).addAccount(account);
    }

    @Test
    void getAccount_ValidAccountId_ReturnsAccount() {
        // Arrange
        Long accountId = 1L;
        Account account = new Account();
        when(accountRepository.getById(accountId)).thenReturn(account);

        // Act
        Account result = accountService.getAccount(accountId);

        // Assert
        assertEquals(account, result);
        verify(accountRepository).getById(accountId);
    }

    @Test
    void getAccount_InvalidAccountId_ReturnsNull() {
        // Arrange
        Long accountId = 1L;
        when(accountRepository.getById(accountId)).thenReturn(null);

        // Act & Assert
        assertNull(accountService.getAccount(accountId));
        verify(accountRepository).getById(accountId);
    }

    @Test
    void getAccountByNumber_ValidAccountNumber_ReturnsAccount() {
        // Arrange
        String accountNumber = "123456789";
        Account account = new Account();
        when(accountRepository.getByAccountNumber(accountNumber)).thenReturn(account);

        // Act
        Account result = accountService.getAccountByNumber(accountNumber);

        // Assert
        assertEquals(account, result);
        verify(accountRepository).getByAccountNumber(accountNumber);
    }

    @Test
    void getAccountByNumber_InvalidAccountNumber_ReturnsNull() {
        // Arrange
        String accountNumber = "123456789";
        when(accountRepository.getByAccountNumber(accountNumber)).thenReturn(null);

        // Act & Assert
        assertNull(accountService.getAccountByNumber(accountNumber));
        verify(accountRepository).getByAccountNumber(accountNumber);
    }

    @Test
    void schedulePayment_ValidScheduledPayment_ReturnsScheduledPayment() {
        // Arrange
        ScheduledPayment scheduledPayment = new ScheduledPayment();
        when(scheduledPaymentRepository.addScheduledPayment(scheduledPayment)).thenReturn(scheduledPayment);

        // Act
        ScheduledPayment result = accountService.schedulePayment(scheduledPayment);

        // Assert
        assertEquals(scheduledPayment, result);
        verify(scheduledPaymentRepository).addScheduledPayment(scheduledPayment);
    }

    @Test
    void schedulePayment_InvalidScheduledPayment_ReturnsNull() {
        // Arrange
        ScheduledPayment scheduledPayment = new ScheduledPayment();
        when(scheduledPaymentRepository.addScheduledPayment(scheduledPayment)).thenReturn(null);

        // Act & Assert
        assertNull(accountService.schedulePayment(scheduledPayment));
        verify(scheduledPaymentRepository).addScheduledPayment(scheduledPayment);
    }

    @Test
    void getScheduledPaymentsOfAccount_ValidAccountId_ReturnsScheduledPayments() {
        // Arrange
        Long accountId = 1L;
        Account account = new Account();
        List<ScheduledPayment> scheduledPayments = new ArrayList<>();
        account.setScheduledPayments(scheduledPayments);
        when(accountRepository.getById(accountId)).thenReturn(account);

        // Act
        List<ScheduledPayment> result = accountService.getScheduledPaymentsOfAccount(accountId);

        // Assert
        assertEquals(scheduledPayments, result);
    }
}
