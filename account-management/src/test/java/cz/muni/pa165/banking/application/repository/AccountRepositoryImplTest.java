package cz.muni.pa165.banking.application.repository;

import cz.muni.pa165.banking.domain.account.Account;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class AccountRepositoryImplTest {
    @InjectMocks
    private AccountRepositoryImpl accountRepository;

    @Test
    void addAccount_ValidAccount_ReturnsAccount() {
        // Arrange
        Account account = new Account();

        // Act
        Account result = accountRepository.addAccount(account);

        // Assert
        assertEquals(account, result);
    }

    @Test
    void getById_ExistingId_ReturnsAccount() {
        // Arrange
        Account account = new Account();
        accountRepository.addAccount(account);
        Long id = account.getId();

        // Act
        Account result = accountRepository.getById(id);

        // Assert
        assertEquals(account, result);
    }

    @Test
    void getById_NonExistingId_ReturnsNull() {
        // Arrange
        Long nonExistingId = 123L;

        // Act
        Account result = accountRepository.getById(nonExistingId);

        // Assert
        assertNull(result);
    }

    @Test
    void getByAccountNumber_ExistingAccountNumber_ReturnsAccount() {
        // Arrange
        Account account = new Account();
        accountRepository.addAccount(account);
        String accountNumber = account.getAccountNumber();

        // Act
        Account result = accountRepository.getByAccountNumber(accountNumber);

        // Assert
        assertEquals(account, result);
    }

    @Test
    void getByAccountNumber_NonExistingAccountNumber_ReturnsNull() {
        // Arrange
        String nonExistingAccountNumber = "NonExistingAccountNumber";

        // Act
        Account result = accountRepository.getByAccountNumber(nonExistingAccountNumber);

        // Assert
        assertNull(result);
    }
}
