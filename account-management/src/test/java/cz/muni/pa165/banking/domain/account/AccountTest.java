package cz.muni.pa165.banking.domain.account;

import cz.muni.pa165.banking.domain.scheduled.ScheduledPayment;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
    @Test
    public void testGettersAndSetters() {
        // Arrange
        Account account = new Account();
        Long id = 1L;
        String accountNumber = "123456789";
        Long userId = 2L;
        Integer maxSpendingLimit = 1000;
        AccountType type = AccountType.CREDIT;
        List<ScheduledPayment> scheduledPayments = new ArrayList<>();

        // Act
        account.setId(id);
        account.setAccountNumber(accountNumber);
        account.setUserId(userId);
        account.setMaxSpendingLimit(maxSpendingLimit);
        account.setType(type);

        // Assert
        assertEquals(id, account.getId());
        assertEquals(accountNumber, account.getAccountNumber());
        assertEquals(userId, account.getUserId());
        assertEquals(maxSpendingLimit, account.getMaxSpendingLimit());
        assertEquals(type, account.getType());
    }

    @Test
    public void testDefaultConstructor() {
        // Act
        Account account = new Account();

        // Assert
        assertNull(account.getId());
        assertNull(account.getAccountNumber());
        assertNull(account.getUserId());
        assertNull(account.getMaxSpendingLimit());
        assertNull(account.getType());
    }

}