package cz.muni.pa165.banking.domain.process.handler;

import cz.muni.pa165.banking.domain.account.Account;
import cz.muni.pa165.banking.domain.money.CurrencyConverter;
import cz.muni.pa165.banking.domain.money.Money;
import cz.muni.pa165.banking.domain.money.exchange.ExchangeRateService;
import cz.muni.pa165.banking.domain.process.Process;
import cz.muni.pa165.banking.domain.process.ProcessMock;
import cz.muni.pa165.banking.domain.process.ProcessOperations;
import cz.muni.pa165.banking.domain.process.ProcessTransaction;
import cz.muni.pa165.banking.domain.process.repository.ProcessRepository;
import cz.muni.pa165.banking.domain.process.repository.ProcessTransactionRepository;
import cz.muni.pa165.banking.domain.process.status.Status;
import cz.muni.pa165.banking.domain.process.status.StatusInformation;
import cz.muni.pa165.banking.domain.remote.AccountService;
import cz.muni.pa165.banking.domain.transaction.TransactionType;
import cz.muni.pa165.banking.exception.EntityNotFoundException;
import cz.muni.pa165.banking.exception.UnexpectedValueException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WithdrawHandlerTest {

    private boolean published = false;

    private static Account account;
    private static Process process;
    private static ProcessTransaction processTransaction;
    private static ProcessRepository processRepository;
    private static ProcessTransactionRepository processTransactionRepository;
    private static ProcessHandler depositHandler;
    private static CurrencyConverter converter;

    @BeforeAll
    static void init() {
        account = new Account("ACC");
        process = new ProcessMock();
        processTransaction = new ProcessTransaction(account, null, TransactionType.WITHDRAWAL, new Money(BigDecimal.ONE, Currency.getInstance("EUR")), "", process.uuid());
        depositHandler = new WithdrawHandler();
        processRepository = mock(ProcessRepository.class);
        processTransactionRepository = mock(ProcessTransactionRepository.class);
        converter = new CurrencyConverterStub(null);

        when(processRepository.findById(process.uuid())).thenReturn(process);
        when(processTransactionRepository.findTransactionByProcessId(process.uuid())).thenReturn(processTransaction);
    }

    @BeforeEach
    void prepare() {
        ProcessOperations.changeState(process, new StatusInformation(null, Status.CREATED, null));
    }

    @Test
    void nonexistingAccountValidation() {
        AccountService accountService = new AccountServiceStub(false, null, false);

        assertThrows(
                EntityNotFoundException.class,
                () -> depositHandler.handle(process.uuid(), processRepository, processTransactionRepository, accountService, converter)
        );
        assertEquals(Status.FAILED, process.getStatus());
    }

    @Test
    void withdrawMoneyOfDifferentBalance() {
        AccountService accountService = new AccountServiceStub(true, Currency.getInstance("CZK"), false);

        assertThrows(
                UnexpectedValueException.class,
                () -> depositHandler.handle(process.uuid(), processRepository, processTransactionRepository, accountService, converter)
        );
        assertEquals(Status.FAILED, process.getStatus());
    }

    @Test
    void insufficientFundsForWithdraw() {
        AccountService accountService = new AccountServiceStub(true, Currency.getInstance("EUR"), false);

        assertThrows(
                UnexpectedValueException.class,
                () -> depositHandler.handle(process.uuid(), processRepository, processTransactionRepository, accountService, converter)
        );
        assertEquals(Status.FAILED, process.getStatus());
    }

    @Test
    void withdrawTransactionSuccessful() {
        AccountService accountService = new AccountServiceStub(true, Currency.getInstance("EUR"), true);
        
        depositHandler.handle(process.uuid(), processRepository, processTransactionRepository, accountService, converter);
        
        assertEquals(Status.PROCESSED, process.getStatus());
        assertTrue(published);
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

        private final boolean isValid;
        private final Currency currency;
        private final boolean sufficientFunds;

        private AccountServiceStub(boolean isValid, Currency currency, boolean sufficientFunds) {
            this.isValid = isValid;
            this.currency = currency;
            this.sufficientFunds = sufficientFunds;
        }

        @Override
        public Currency getAccountCurrency(Account account) {
            return currency;
        }

        @Override
        public boolean isValid(Account account) {
            return isValid;
        }

        @Override
        public boolean accountHasSufficientFunds(Account account, BigDecimal amount) {
            return sufficientFunds;
        }

        @Override
        public void publishAccountChange(UUID processUuid, TransactionType transactionType, BigDecimal amount, Account account, String information) {
            published = true;
        }
    }
}