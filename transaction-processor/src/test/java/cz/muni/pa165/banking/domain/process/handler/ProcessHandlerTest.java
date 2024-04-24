package cz.muni.pa165.banking.domain.process.handler;

import cz.muni.pa165.banking.domain.account.Account;
import cz.muni.pa165.banking.domain.money.CurrencyConverter;
import cz.muni.pa165.banking.domain.money.Money;
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
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProcessHandlerTest {
    
    private static ProcessHandler genericHandler;
    private static ProcessRepository processRepository;
    private static ProcessTransactionRepository processTransactionRepository;

    @BeforeAll
    static void init() {
        genericHandler = new ProcessHandler() {
            @Override
            void evaluate(ProcessTransaction processTransaction, AccountService accountService, CurrencyConverter currencyConverter) {

            }
        };

        processRepository = mock(ProcessRepository.class);
        processTransactionRepository = mock(ProcessTransactionRepository.class);
    }

    @Test
    void processValidationFailedStatus() {
        Process process = createProcess();
        ProcessOperations.changeState(process, new StatusInformation(null, Status.FAILED, "N/A"));
        assertThrows(
                UnexpectedValueException.class,
                () -> genericHandler.handle(process.getUuid(), processRepository, null, null, null, null)
        );
    }


    @Test
    void processValidationProcessedStatus() {
        Process process = createProcess();
        ProcessOperations.changeState(process, new StatusInformation(null, Status.PROCESSED, "N/A"));
        assertThrows(
                UnexpectedValueException.class,
                () -> genericHandler.handle(process.getUuid(), processRepository, null, null, null, null)
        );
    }

    @Test
    void processNotLinkedToTransactionRequest() {
        Process process = createProcess();
        when(processTransactionRepository.findTransactionByProcessId(process.getUuid())).thenReturn(null);
        assertThrows(
                EntityNotFoundException.class,
                () -> genericHandler.handle(process.getUuid(), processRepository, processTransactionRepository, null, null, null)
        );
    }
    
    @Test
    void processEvaluatedAsFailed() {
        ProcessHandler failingHandler = new ProcessHandler() {
            @Override
            void evaluate(ProcessTransaction processTransaction, AccountService accountService, CurrencyConverter currencyConverter) {
                throw new RuntimeException("Bad news boss");
            }
        };

        Process process = createProcess();
        ofProcess(process);
        
        assertThrows(
                RuntimeException.class,
                () -> failingHandler.handle(process.getUuid(), processRepository, processTransactionRepository, null, null, null)
        );

        assertEquals(Status.FAILED, process.getStatus());
    }

    @Test
    void processEvaluatedAsProcessed() {
        Process process = createProcess();
        ofProcess(process);

        genericHandler.handle(process.getUuid(), processRepository, processTransactionRepository, null, null, null);
        assertEquals(Status.PROCESSED, process.getStatus());
    }


    private Process createProcess() {
        Process process = new ProcessMock();
        when(processRepository.findById(process.getUuid())).thenReturn(process);
        return process;
    }
    
    private ProcessTransaction ofProcess(Process process) {
        ProcessTransaction result = new ProcessTransaction(new Account("ACC_1"), null, TransactionType.DEPOSIT, new Money(BigDecimal.ONE, Currency.getInstance("EUR")), "", process.getUuid());
        when(processTransactionRepository.findTransactionByProcessId(process.getUuid())).thenReturn(result);
        return result;
    }
}