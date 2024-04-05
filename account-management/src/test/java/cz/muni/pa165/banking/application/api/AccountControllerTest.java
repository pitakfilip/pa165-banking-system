package cz.muni.pa165.banking.application.api;

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
    void getAccount_ValidAccountNumber_ReturnsAccount() {
        // Arrange
        String accountNumber = "123456789";
        AccountDto accountDto = new AccountDto();
        when(accountFacade.getAccount(accountNumber)).thenReturn(accountDto);

        // Act
        ResponseEntity<AccountDto> responseEntity = accountController.getAccount(accountNumber);

        // Assert
        assertNotNull(responseEntity.getBody());
        verify(accountFacade).getAccount(accountNumber);
    }

    @Test
    void getAccount_InvalidAccountNumber_ReturnsNull() {
        // Arrange
        String invalidAccountNumber = "123456789";
        when(accountFacade.getAccount(invalidAccountNumber)).thenReturn(null);

        // Act
        ResponseEntity<AccountDto> responseEntity = accountController.getAccount(invalidAccountNumber);

        // Assert
        assertNull(responseEntity.getBody());
        verify(accountFacade).getAccount(invalidAccountNumber);
    }

    @Test
    void getScheduledPayments_ValidAccountNumber_ReturnsPayments() {
        // Arrange
        String accountNumber = "123456789";
        ScheduledPaymentsDto scheduledPaymentsDto = new ScheduledPaymentsDto();
        when(accountFacade.getScheduledPaymentsOfAccount(accountNumber)).thenReturn(scheduledPaymentsDto);

        // Act
        ResponseEntity<ScheduledPaymentsDto> responseEntity = accountController.getScheduledPayments(accountNumber);

        // Assert
        assertNotNull(responseEntity.getBody());
        verify(accountFacade).getScheduledPaymentsOfAccount(accountNumber);
    }

    @Test
    void getScheduledPayments_InvalidAccountNumber_ReturnsNull() {
        // Arrange
        String invalidAccountNumber = "123456789";
        when(accountFacade.getScheduledPaymentsOfAccount(invalidAccountNumber)).thenReturn(null);

        // Act
        ResponseEntity<ScheduledPaymentsDto> responseEntity = accountController.getScheduledPayments(invalidAccountNumber);

        // Assert
        assertNull(responseEntity.getBody());
        verify(accountFacade).getScheduledPaymentsOfAccount(invalidAccountNumber);
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
