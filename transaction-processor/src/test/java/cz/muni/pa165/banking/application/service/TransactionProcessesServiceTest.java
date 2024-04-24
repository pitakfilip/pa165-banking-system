package cz.muni.pa165.banking.application.service;

import cz.muni.pa165.banking.domain.account.Account;
import cz.muni.pa165.banking.domain.messaging.MessageProducer;
import cz.muni.pa165.banking.domain.money.Money;
import cz.muni.pa165.banking.domain.process.Process;
import cz.muni.pa165.banking.domain.process.ProcessOperations;
import cz.muni.pa165.banking.domain.process.ProcessTransaction;
import cz.muni.pa165.banking.domain.process.repository.ProcessRepository;
import cz.muni.pa165.banking.domain.process.repository.ProcessTransactionRepository;
import cz.muni.pa165.banking.domain.process.status.Status;
import cz.muni.pa165.banking.domain.process.status.StatusInformation;
import cz.muni.pa165.banking.domain.transaction.Transaction;
import cz.muni.pa165.banking.domain.transaction.TransactionType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionProcessesServiceTest {

    @Mock
    private ProcessTransactionRepository processTransactionRepository;

    @Mock
    private ProcessRepository processRepository;

    @Mock
    private MessageProducer processProducer;

    @InjectMocks
    private TransactionProcessesService transactionProcessesService;

    @Test
    void createProcessForTransaction_shouldCreateProcess() {
        Transaction newTransaction = new Transaction();

        transactionProcessesService.createProcessForTransaction(newTransaction);

        verify(processRepository, times(1)).save(any());
        verify(processTransactionRepository, times(1)).save(any());
        verify(processProducer, times(1)).send(any());
    }

    @Test
    void findProcess_shouldCallProcessRepository() {
        UUID uuid = UUID.randomUUID();

        transactionProcessesService.findProcess(uuid);

        verify(processRepository, times(1)).findById(uuid);
    }

    @Test
    void findProcessTransaction_shouldCallProcessTransactionRepository() {
        UUID uuid = UUID.randomUUID();

        transactionProcessesService.findProcessTransaction(uuid);

        verify(processTransactionRepository, times(1)).findTransactionByProcessId(uuid);
    }

    @Test
    void revertProcess_shouldCreateRevertingProcess() {
        UUID uuid = UUID.randomUUID();
        Process process = Process.createNew();
        ProcessOperations.changeState(process, new StatusInformation(Instant.now(), Status.PROCESSED, "done"));
        ProcessTransaction processTransaction = new ProcessTransaction();
        processTransaction.setUuid(process.getUuid());
        processTransaction.setSource(new Account("123"));
        processTransaction.setTarget(new Account("321"));
        processTransaction.setType(TransactionType.TRANSFER);
        processTransaction.setMoney(new Money(BigDecimal.ONE, Currency.getInstance("EUR")));
        Transaction revertingTransaction = new Transaction();

        when(processRepository.findById(uuid)).thenReturn(process);
        when(processTransactionRepository.findTransactionByProcessId(uuid)).thenReturn(processTransaction);

        transactionProcessesService.revertProcess(uuid);

        verify(processRepository, times(1)).findById(any());
        verify(processTransactionRepository, times(1)).findTransactionByProcessId(any());
        verify(processRepository, times(1)).save(any());
        verify(processProducer, times(1)).send(any());
    }
}