package cz.muni.pa165.banking.application.service;

import cz.muni.pa165.banking.account.query.SystemServiceApi;
import cz.muni.pa165.banking.domain.account.Account;
import cz.muni.pa165.banking.domain.account.repository.AccountRepository;
import cz.muni.pa165.banking.domain.scheduled.ScheduledPayment;
import cz.muni.pa165.banking.domain.scheduled.ScheduledPaymentProjection;
import cz.muni.pa165.banking.domain.scheduled.repository.ScheduledPaymentRepository;
import cz.muni.pa165.banking.domain.user.repository.UserRepository;
import cz.muni.pa165.banking.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ScheduledPaymentRepository scheduledPaymentRepository;

    @Mock
    private SystemServiceApi balanceApi;

    @InjectMocks
    private AccountService accountService;

    @Test
    void createAccount_ValidAccount_ReturnsAccount() {
        // Arrange
        Account account = new Account();
        account.setId(1L);
        account.setAccountNumber("123");
        account.setUserId(1L);

        when(userRepository.existsById(anyLong())).thenReturn(true);
        when(accountRepository.save(account)).thenReturn(account);

        // Act
        Account result = accountService.createAccount(account);

        // Assert
        assertEquals(account, result);
        verify(accountRepository).save(account);
    }

    @Test
    void getAccount_ValidAccountId_ReturnsAccount(){
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
    void getAccountById_InvalidAccountId_ThrowsException(){
        // Arrange
        Long accountId = 1L;
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            accountService.findById(accountId);
        });

        verify(accountRepository).findById(accountId);
    }

    @Test
    void getAccountByNumber_ValidAccountNumber_ReturnsAccount(){
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
    void getAccountByNumber_InvalidAccountNumber_ThrowsException(){
        // Arrange
        String accountNumber = "123456789";
        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            accountService.findByNumber(accountNumber);
        });
        verify(accountRepository).findByAccountNumber(accountNumber);
    }

    @Test
    void schedulePayment_ValidScheduledPayment_ReturnsScheduledPayment() {
        // Arrange
        ScheduledPayment scheduledPayment = new ScheduledPayment();
        scheduledPayment.setId(1L);
        scheduledPayment.setSourceAccountId(1L);
        scheduledPayment.setTargetAccountId(2L);

        when(accountRepository.existsById(anyLong())).thenReturn(true);
        when(scheduledPaymentRepository.save(scheduledPayment)).thenReturn(scheduledPayment);

        // Act
        ScheduledPayment result = accountService.schedulePayment(scheduledPayment);

        // Assert
        assertEquals(scheduledPayment, result);
        verify(scheduledPaymentRepository).save(scheduledPayment);
    }

    @Test
    void schedulePayment_InvalidScheduledPayment_ThrowsException(){
        // Arrange
        ScheduledPayment scheduledPayment = new ScheduledPayment();

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            accountService.schedulePayment(scheduledPayment);
        });
    }

    @Test
    void getScheduledPaymentsOfAccount_ValidAccountId_ReturnsScheduledPayments() {
        // Arrange
        String accountNumber = "123123123";
        Account account = new Account();
        List<ScheduledPayment> scheduledPayments = new ArrayList<>();
        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.of(account));

        // Act
        List<ScheduledPaymentProjection> result = accountService.findScheduledPaymentsByAccount(accountNumber);

        // Assert
        assertEquals(scheduledPayments, result);
    }
}