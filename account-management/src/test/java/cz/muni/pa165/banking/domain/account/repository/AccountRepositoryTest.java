package cz.muni.pa165.banking.domain.account.repository;

import cz.muni.pa165.banking.domain.account.Account;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountRepositoryTest {
    @Mock
    private AccountRepository accountRepository;

    @Test
    void findByAccountNumber_ValidNumber_ReturnsAccount() {
        // Given
        String validAccountNumber = "123456";
        Account account = new Account();
        account.setId(1L);
        account.setAccountNumber(validAccountNumber);

        when(accountRepository.findByAccountNumber(validAccountNumber)).thenReturn(Optional.of(account));

        // When
        Optional<Account> foundAccountOptional = accountRepository.findByAccountNumber(validAccountNumber);
        Account foundAccount = foundAccountOptional.orElse(null);

        // Then
        assertNotNull(foundAccount);
        assertEquals(1L, foundAccount.getId());
        assertEquals(validAccountNumber, foundAccount.getAccountNumber());
    }

    @Test
    void findByAccountNumber_InvalidNumber_ReturnsNull() {
        // Given
        String invalidAccountNumber = "999999";

        when(accountRepository.findByAccountNumber(invalidAccountNumber)).thenReturn(Optional.empty());

        // When
        Optional<Account> foundAccountOptional = accountRepository.findByAccountNumber(invalidAccountNumber);
        Account foundAccount = foundAccountOptional.orElse(null);

        // Then
        assertNull(foundAccount);
    }
}