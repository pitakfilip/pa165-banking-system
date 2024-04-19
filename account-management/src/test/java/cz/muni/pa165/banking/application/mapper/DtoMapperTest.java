package cz.muni.pa165.banking.application.mapper;

import cz.muni.pa165.banking.account.management.dto.AccountDto;
import cz.muni.pa165.banking.account.management.dto.ScheduledPaymentDto;
import cz.muni.pa165.banking.domain.scheduled.ScheduledPayment;
import cz.muni.pa165.banking.domain.user.UserType;
import cz.muni.pa165.banking.domain.account.Account;
import cz.muni.pa165.banking.domain.account.AccountType;
import cz.muni.pa165.banking.account.management.dto.UserDto;
import cz.muni.pa165.banking.domain.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DtoMapperTest {
    private final DtoMapper mapper = Mappers.getMapper(DtoMapper.class);

    @Test
    public void mapUserToUserDto() {
        // Arrange
        User user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUserType(UserType.EMPLOYEE);

        // Act
        UserDto userDto = mapper.map(user);

        // Assert
        assertEquals(user.getId(), userDto.getId());
        assertEquals(user.getFirstName(), userDto.getFirstName());
        assertEquals(user.getLastName(), userDto.getLastName());
        assertEquals(user.getUserType(), mapper.map(userDto.getUserType()));
    }

    @Test
    public void mapScheduledPaymentToScheduledPaymentDto() {
        // Arrange
        ScheduledPayment scheduledPayment = new ScheduledPayment();
        scheduledPayment.setId(1L);
        scheduledPayment.setAmount(100);

        // Act
        ScheduledPaymentDto scheduledPaymentDto = mapper.map(scheduledPayment);

        // Assert
        assertEquals(scheduledPayment.getId(), scheduledPaymentDto.getId());
        assertEquals(scheduledPayment.getAmount(), scheduledPaymentDto.getAmount());
    }

    @Test
    public void mapAccountToAccountDto() {
        // Arrange
        Account account = new Account();
        account.setId(1L);
        account.setUserId(2L);
        account.setMaxSpendingLimit(500);
        account.setType(AccountType.SAVING);
        account.setCurrency(Currency.getInstance("USD"));

        // Act
        AccountDto accountDto = mapper.map(account);

        // Assert
        assertEquals(account.getId(), accountDto.getId());
        assertEquals(account.getUserId(), accountDto.getUserId());
        assertEquals(account.getMaxSpendingLimit(), accountDto.getMaxSpendingLimit());
        assertEquals(account.getType(), mapper.map(accountDto.getType()));
    }
}