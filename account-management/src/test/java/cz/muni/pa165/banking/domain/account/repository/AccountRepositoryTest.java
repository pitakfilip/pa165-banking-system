package cz.muni.pa165.banking.domain.account.repository;

import cz.muni.pa165.banking.domain.account.Account;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
class AccountRepositoryTest {
    @Autowired
    private AccountRepository accountRepository;

    @BeforeAll
    public static void initDb(@Autowired AccountRepository repository) {
        Account account = new Account();
        account.setId(1L);
        account.setAccountNumber("1");
        repository.save(account);
    }


    @Test
    void findByAccountNumber_ValidNumber_ReturnsAccount() {
        // Arrange
        Account expectedAccount = new Account();
        expectedAccount.setId(1L);
        expectedAccount.setAccountNumber("1");

        // Act
        Optional<Account> result = accountRepository.findByAccountNumber("1");

        // Assert
        assertEquals(expectedAccount, result.orElse(null));
    }

    @Test
    void findByAccountNumber_InvalidNumber_ReturnsEmptyOptional() {
        // Act
        Optional<Account> result = accountRepository.findByAccountNumber("2");

        // Assert
        assertFalse(result.isPresent());
    }
}