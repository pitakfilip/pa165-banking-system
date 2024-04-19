package cz.muni.pa165.banking.application.mapper;

import cz.muni.pa165.banking.account.management.dto.*;
import cz.muni.pa165.banking.domain.account.Account;
import cz.muni.pa165.banking.domain.account.AccountType;
import cz.muni.pa165.banking.domain.user.User;
import cz.muni.pa165.banking.domain.scheduled.ScheduledPayment;
import cz.muni.pa165.banking.domain.user.UserType;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.Currency;
import java.util.List;
import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface DtoMapper {

    UserType map(UserTypeDto userTypeDto);

    UserTypeDto map(UserType userType);

    ScheduledPayment map(ScheduledPaymentDto scheduledPaymentDto);

    ScheduledPaymentDto map(ScheduledPayment scheduledPayment);

    User map(UserDto userDto);

    UserDto map(User user);

    AccountTypeDto map(AccountType accountType);

    AccountType map(AccountTypeDto accountTypeDto);

    default Account map(NewAccountDto newAccountDto){
        Account account = new Account();
        account.setId(Math.abs(UUID.randomUUID().getMostSignificantBits()));
        account.setAccountNumber(UUID.randomUUID().toString());
        account.setUserId(newAccountDto.getUserId());
        account.setMaxSpendingLimit(newAccountDto.getMaxSpendingLimit());
        account.setType(map(newAccountDto.getType()));
        account.setCurrency(Currency.getInstance(newAccountDto.getCurrency()));
        return account;
    }

    default User map(NewUserDto newUserDto){
        User user = new User();
        user.setId(Math.abs(UUID.randomUUID().getMostSignificantBits()));
        user.setEmail(newUserDto.getEmail());
        user.setPassword(newUserDto.getPassword());
        user.setFirstName(newUserDto.getFirstName());
        user.setLastName(newUserDto.getLastName());
        user.setUserType(map(newUserDto.getUserType()));
        return user;
    }

    default ScheduledPaymentsDto map(List<ScheduledPayment> scheduledPayments){
        ScheduledPaymentsDto result = new ScheduledPaymentsDto();
        result.setScheduledPayments(scheduledPayments.stream()
                .map(this::map).toList());
        return result;
    }

    default Account map(AccountDto accountDto){
        Account account = new Account();
        account.setId(accountDto.getId());
        account.setAccountNumber(UUID.randomUUID().toString());
        account.setUserId(accountDto.getUserId());
        account.setMaxSpendingLimit(accountDto.getMaxSpendingLimit());
        account.setType(map(accountDto.getType()));
        account.setCurrency(Currency.getInstance(accountDto.getCurrency()));
        return account;
    }

    default AccountDto map(Account account){
        AccountDto accountDto = new AccountDto();
        accountDto.setId(account.getId());
        accountDto.setUserId(account.getUserId());
        accountDto.setMaxSpendingLimit(account.getMaxSpendingLimit());
        accountDto.setType(map(account.getType()));
        accountDto.setCurrency(account.getCurrency().getCurrencyCode());
        return accountDto;
    }
}
