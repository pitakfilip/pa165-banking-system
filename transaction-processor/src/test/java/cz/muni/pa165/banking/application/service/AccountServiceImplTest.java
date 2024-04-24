package cz.muni.pa165.banking.application.service;

import cz.muni.pa165.banking.account.management.AccountApi;
import cz.muni.pa165.banking.account.management.dto.AccountDto;
import cz.muni.pa165.banking.account.query.CustomerServiceApi;
import cz.muni.pa165.banking.account.query.SystemServiceApi;
import cz.muni.pa165.banking.domain.account.Account;
import cz.muni.pa165.banking.domain.transaction.TransactionType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock
    private AccountApi accountApi;

    @Mock
    private CustomerServiceApi publicBalanceApi;

    @Mock
    private SystemServiceApi privateBalanceApi;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    void getAccountCurrency_shouldCallAccountApi() {
        Account account = new Account("12345");
        AccountDto accountDto = new AccountDto();
        accountDto.setCurrency("USD");

        when(accountApi.findByAccountNumber(account.getAccountNumber())).thenReturn(ResponseEntity.ok(accountDto));

        accountService.getAccountCurrency(account);

        verify(accountApi, times(1)).findByAccountNumber(account.getAccountNumber());
    }

    @Test
    void isValid_shouldCallAccountApi() {
        Account account = new Account("12345");
        AccountDto accountDto = new AccountDto();

        when(accountApi.findByAccountNumber(account.getAccountNumber())).thenReturn(ResponseEntity.ok(accountDto));

        accountService.isValid(account);

        verify(accountApi, times(1)).findByAccountNumber(account.getAccountNumber());
    }

    @Test
    void accountHasSufficientFunds_shouldCallPublicBalanceApi() {
        Account account = new Account("12345");
        BigDecimal amount = BigDecimal.TEN;

        when(publicBalanceApi.getBalance(account.getAccountNumber())).thenReturn(ResponseEntity.ok(BigDecimal.ONE));

        accountService.accountHasSufficientFunds(account, amount);

        verify(publicBalanceApi, times(1)).getBalance(account.getAccountNumber());
    }

    @Test
    void publishAccountChange_shouldCallPrivateBalanceApi() {
        UUID processUuid = UUID.randomUUID();
        TransactionType transactionType = TransactionType.DEPOSIT;
        BigDecimal amount = BigDecimal.TEN;
        Account account = new Account("12345");

        accountService.publishAccountChange(processUuid, transactionType, amount, account);

        verify(privateBalanceApi, times(1)).addTransactionToBalance(
                account.getAccountNumber(),
                amount,
                processUuid,
                cz.muni.pa165.banking.account.query.dto.TransactionType.DEPOSIT
        );
    }
}