package cz.muni.pa165.banking.domain.process.handler;

import cz.muni.pa165.banking.domain.account.Account;
import cz.muni.pa165.banking.domain.money.CurrencyConverter;
import cz.muni.pa165.banking.domain.money.Money;
import cz.muni.pa165.banking.domain.money.exchange.ExchangeRateService;
import cz.muni.pa165.banking.domain.process.Process;
import cz.muni.pa165.banking.domain.process.ProcessMock;
import cz.muni.pa165.banking.domain.process.ProcessTransaction;
import cz.muni.pa165.banking.domain.process.repository.ProcessRepository;
import cz.muni.pa165.banking.domain.process.repository.ProcessTransactionRepository;
import cz.muni.pa165.banking.domain.process.status.Status;
import cz.muni.pa165.banking.domain.remote.AccountService;
import cz.muni.pa165.banking.domain.transaction.TransactionType;
import cz.muni.pa165.banking.exception.EntityNotFoundException;
import cz.muni.pa165.banking.exception.UnexpectedValueException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ScheduledHandlerTest {

    private boolean published1 = false;
    private boolean published2 = false;

    private static Account account1;
    private static Account account2;
    private static ProcessRepository processRepository;
    private static ProcessTransactionRepository processTransactionRepository;
    private static ProcessHandler depositHandler;
    private static CurrencyConverter converter;

    @BeforeAll
    static void init() {
        account1 = new Account("ACC1");
        account2 = new Account("ACC2");
        depositHandler = new ScheduledHandler();
        processRepository = mock(ProcessRepository.class);
        processTransactionRepository = mock(ProcessTransactionRepository.class);
        converter = new CurrencyConverterStub(null);
    }

    @Test
    void nonexistingFirstAccountValidation() {
        AccountService accountService = new AccountServiceStub(false, false,null, false);
        Process process = new ProcessMock();
        ProcessTransaction processTransaction = new ProcessTransaction(account1, account2, TransactionType.TRANSFER, new Money(BigDecimal.ONE, Currency.getInstance("EUR")), "", process.uuid());

        when(processRepository.findById(process.uuid())).thenReturn(process);
        when(processTransactionRepository.findTransactionByProcessId(process.uuid())).thenReturn(processTransaction);
        
        assertThrows(
                EntityNotFoundException.class,
                () -> depositHandler.handle(process.uuid(), processRepository, processTransactionRepository, accountService, converter)
        );
        assertEquals(Status.FAILED, process.getStatus());
    }

    @Test
    void twoSameAccountsValidation() {
        AccountService accountService = new AccountServiceStub(true, false,null, false);
        Process process = new ProcessMock();
        ProcessTransaction processTransaction = new ProcessTransaction(account1, account1, TransactionType.TRANSFER, new Money(BigDecimal.ONE, Currency.getInstance("EUR")), "", process.uuid());

        when(processRepository.findById(process.uuid())).thenReturn(process);
        when(processTransactionRepository.findTransactionByProcessId(process.uuid())).thenReturn(processTransaction);

        assertThrows(
                UnexpectedValueException.class,
                () -> depositHandler.handle(process.uuid(), processRepository, processTransactionRepository, accountService, converter)
        );
        assertEquals(Status.FAILED, process.getStatus());
    }

    @Test
    void nonexistingSecondAccountValidation() {
        AccountService accountService = new AccountServiceStub(true, false,null, false);
        Process process = new ProcessMock();
        ProcessTransaction processTransaction = new ProcessTransaction(account1, account2, TransactionType.TRANSFER, new Money(BigDecimal.ONE, Currency.getInstance("EUR")), "", process.uuid());

        when(processRepository.findById(process.uuid())).thenReturn(process);
        when(processTransactionRepository.findTransactionByProcessId(process.uuid())).thenReturn(processTransaction);
        
        assertThrows(
                EntityNotFoundException.class,
                () -> depositHandler.handle(process.uuid(), processRepository, processTransactionRepository, accountService, converter)
        );
        assertEquals(Status.FAILED, process.getStatus());
    }

    @Test
    void crossAccountTransactionSuccessful() {
        AccountService accountService = new AccountServiceStub(true, true, null, true);
        Process process = new ProcessMock();
        ProcessTransaction  processTransaction = new ProcessTransaction(account1, account2, TransactionType.TRANSFER, new Money(BigDecimal.ONE, Currency.getInstance("EUR")), "", process.uuid());

        when(processRepository.findById(process.uuid())).thenReturn(process);
        when(processTransactionRepository.findTransactionByProcessId(process.uuid())).thenReturn(processTransaction);

        depositHandler.handle(process.uuid(), processRepository, processTransactionRepository, accountService, converter);

        assertEquals(Status.PROCESSED, process.getStatus());
        assertTrue(published1);
        assertTrue(published2);
    }


    private static class CurrencyConverterStub extends CurrencyConverter {

        public CurrencyConverterStub(ExchangeRateService exchangeRateApi) {
            super(exchangeRateApi);
        }

        @Override
        public BigDecimal convertTo(Currency source, Currency target, BigDecimal amount) {
            return amount;
        }
    }

    private class AccountServiceStub implements AccountService {

        private final boolean isValid1;
        private final boolean isValid2;
        private final Currency currency;
        private final boolean sufficientFunds;

        private AccountServiceStub(boolean isValid1, boolean isValid2, Currency currency, boolean sufficientFunds) {
            this.isValid1 = isValid1;
            this.isValid2 = isValid2;
            this.currency = currency;
            this.sufficientFunds = sufficientFunds;
        }

        @Override
        public Currency getAccountCurrency(Account account) {
            return currency;
        }

        @Override
        public boolean isValid(Account account) {
            if (account.equals(account1)) {
                return isValid1;
            }
            return isValid2;
        }

        @Override
        public boolean accountHasSufficientFunds(Account account, BigDecimal amount) {
            return sufficientFunds;
        }

        @Override
        public void publishAccountChange(UUID processUuid, TransactionType transactionType, BigDecimal amount, Account account, String information) {
            if (account.equals(account1)) {
                published1 = true;
            } else {
                published2 = true;
            }
        }
    }
}