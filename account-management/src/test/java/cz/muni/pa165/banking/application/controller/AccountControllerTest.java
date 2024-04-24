package cz.muni.pa165.banking.application.controller;

import cz.muni.pa165.banking.account.management.dto.AccountDto;
import cz.muni.pa165.banking.account.management.dto.NewAccountDto;
import cz.muni.pa165.banking.account.management.dto.ScheduledPaymentDto;
import cz.muni.pa165.banking.account.management.dto.ScheduledPaymentsDto;
import cz.muni.pa165.banking.application.facade.AccountFacade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest {
    @Mock
    private AccountFacade accountFacade;
    @InjectMocks
    private AccountController accountController;

    @Test
    void createAccount_ValidRequest_ReturnsAccount() {
        // Arrange
        NewAccountDto newAccountDto = new NewAccountDto();
        AccountDto createdAccountDto = new AccountDto();
        when(accountFacade.createAccount(any(NewAccountDto.class))).thenReturn(createdAccountDto);

        // Act
        ResponseEntity<AccountDto> responseEntity = accountController.createAccount(newAccountDto);

        // Assert
        assertNotNull(responseEntity.getBody());
        verify(accountFacade).createAccount(newAccountDto);
    }

    @Test
    void getAccount_ValidAccountNumber_ReturnsAccount(){
        // Arrange
        Long accountId = 123456789L;
        AccountDto accountDto = new AccountDto();
        when(accountFacade.findById(accountId)).thenReturn(accountDto);

        // Act
        ResponseEntity<AccountDto> responseEntity = accountController.findAccountById(accountId);

        // Assert
        assertNotNull(responseEntity.getBody());
        verify(accountFacade).findById(accountId);
    }

    @Test
    void getAccount_InvalidAccountNumber_ReturnsNull() {
        // Arrange
        Long invalidId = 123456789L;
        when(accountFacade.findById(invalidId)).thenReturn(null);

        // Act
        ResponseEntity<AccountDto> responseEntity = accountController.findAccountById(invalidId);

        // Assert
        assertNull(responseEntity.getBody());
        verify(accountFacade).findById(invalidId);
    }

    @Test
    void getScheduledPayments_ValidAccountNumber_ReturnsPayments(){
        // Arrange
        String accountNumber = "123456789";
        ScheduledPaymentsDto scheduledPaymentsDto = new ScheduledPaymentsDto();
        when(accountFacade.findScheduledPaymentsByNumber(accountNumber)).thenReturn(scheduledPaymentsDto);

        // Act
        ResponseEntity<ScheduledPaymentsDto> responseEntity = accountController.getScheduledPayments(accountNumber);

        // Assert
        assertNotNull(responseEntity.getBody());
        verify(accountFacade).findScheduledPaymentsByNumber(accountNumber);
    }

    @Test
    void getScheduledPayments_InvalidAccountNumber_ReturnsNull(){
        // Arrange
        String invalidAccountNumber = "123456789";
        when(accountFacade.findScheduledPaymentsByNumber(invalidAccountNumber)).thenReturn(null);

        // Act
        ResponseEntity<ScheduledPaymentsDto> responseEntity = accountController.getScheduledPayments(invalidAccountNumber);

        // Assert
        assertNull(responseEntity.getBody());
        verify(accountFacade).findScheduledPaymentsByNumber(invalidAccountNumber);
    }

    @Test
    void schedulePayment_ValidRequest_ReturnsPayment() {
        // Arrange
        ScheduledPaymentDto scheduledPaymentDto = new ScheduledPaymentDto();
        when(accountFacade.schedulePayment(any(ScheduledPaymentDto.class))).thenReturn(scheduledPaymentDto);

        // Act
        ResponseEntity<ScheduledPaymentDto> responseEntity = accountController.schedulePayment(scheduledPaymentDto);

        // Assert
        assertNotNull(responseEntity.getBody());
        verify(accountFacade).schedulePayment(scheduledPaymentDto);
    }
}
