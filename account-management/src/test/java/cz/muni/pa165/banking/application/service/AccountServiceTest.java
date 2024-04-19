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
import java.util.Optional;

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
        when(accountRepository.save(account)).thenReturn(account);

        // Act
        Account result = accountService.createAccount(account);

        // Assert
        assertEquals(account, result);
        verify(accountRepository).save(account);
    }

    @Test
    void getAccount_ValidAccountId_ReturnsAccount() throws Exception {
        // Arrange
        Long accountId = 1L;
        Account account = new Account();
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        // Act
        Account result = accountService.findById(accountId);

        // Assert
        assertEquals(account, result);
        verify(accountRepository).findById(accountId);
    }

    @Test
    void getAccountById_InvalidAccountId_ThrowsException() throws Exception {
        // Arrange
        Long accountId = 1L;
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(Exception.class, () -> {
            accountService.findById(accountId);
        });

        verify(accountRepository).findById(accountId);
    }

    @Test
    void getAccountByNumber_ValidAccountNumber_ReturnsAccount() throws Exception {
        // Arrange
        String accountNumber = "123456789";
        Account account = new Account();
        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.of(account));

        // Act
        Account result = accountService.findByNumber(accountNumber);

        // Assert
        assertEquals(account, result);
        verify(accountRepository).findByAccountNumber(accountNumber);
    }

    @Test
    void getAccountByNumber_InvalidAccountNumber_ThrowsException() throws Exception {
        // Arrange
        String accountNumber = "123456789";
        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(Exception.class, () -> {
            accountService.findByNumber(accountNumber);
        });
        verify(accountRepository).findByAccountNumber(accountNumber);
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
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        // Act
        List<ScheduledPayment> result = accountService.findScheduledPaymentsById(accountId);

        // Assert
        assertEquals(scheduledPayments, result);
    }
}
