package cz.muni.pa165.banking.domain.process;

import cz.muni.pa165.banking.domain.account.Account;
import cz.muni.pa165.banking.domain.messaging.MessageProducer;
import cz.muni.pa165.banking.domain.messaging.ProcessRequest;
import cz.muni.pa165.banking.domain.money.Money;
import cz.muni.pa165.banking.domain.process.repository.ProcessRepository;
import cz.muni.pa165.banking.domain.process.repository.ProcessTransactionRepository;
import cz.muni.pa165.banking.domain.process.status.Status;
import cz.muni.pa165.banking.domain.transaction.Transaction;
import cz.muni.pa165.banking.domain.transaction.TransactionType;
import org.awaitility.reflect.WhiteboxImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


class ProcessFactoryTest {

    private final Transaction transaction = new Transaction(
            new Account("ACC_1"),
            null,
            TransactionType.DEPOSIT,
            new Money(BigDecimal.ONE, Currency.getInstance("EUR")),
            null
    );

    @Test
    void returnedObjectSameAsSaved() {
        ProcessTransactionRepository processTransactionRepository = mock(ProcessTransactionRepository.class);
        ProcessRepository processRepository = mock(ProcessRepository.class);
        MessageProducer messageProducer = mock(MessageProducer.class);
        ArgumentCaptor<Process> argumentCaptor = ArgumentCaptor.forClass(Process.class);

        ProcessFactory processFactory = new ProcessFactory(processTransactionRepository, processRepository);
        Process createdProcess = processFactory.create(transaction, messageProducer);

        verify(processRepository).save(argumentCaptor.capture());
        Process captured = argumentCaptor.getValue();

        assertEquals(createdProcess, captured);
    }

    @Test
    void newProcessSetStatusCreated() {
        ProcessTransactionRepository processTransactionRepository = mock(ProcessTransactionRepository.class);
        ProcessRepository processRepository = mock(ProcessRepository.class);
        MessageProducer messageProducer = mock(MessageProducer.class);
        
        ProcessFactory processFactory = new ProcessFactory(processTransactionRepository, processRepository);
        Process createdProcess = processFactory.create(transaction, messageProducer);

        assertEquals(Status.CREATED, createdProcess.getStatus());
    }


    @Test
    void assignedTransactionFilledWithTransactionData() {
        ProcessTransactionRepository processTransactionRepository = mock(ProcessTransactionRepository.class);
        ProcessRepository processRepository = mock(ProcessRepository.class);
        MessageProducer messageProducer = mock(MessageProducer.class);
        ArgumentCaptor<ProcessTransaction> argumentCaptor = ArgumentCaptor.forClass(ProcessTransaction.class);

        ProcessFactory processFactory = new ProcessFactory(processTransactionRepository, processRepository);
        processFactory.create(transaction, messageProducer);


        verify(processTransactionRepository).save(argumentCaptor.capture());

        ProcessTransaction captured = argumentCaptor.getValue();
        assertEquals(transaction.getSource(), captured.getSource());
        assertEquals(transaction.getTarget(), captured.getTarget());
        assertEquals(transaction.getType(), captured.getType());
        assertEquals(transaction.getMoney(), captured.getMoney());
        assertEquals(transaction.getDetail(), captured.getDetail());
    }

    @Test
    void assignedUuidOfProcessToTransaction() {
        ProcessTransactionRepository processTransactionRepository = mock(ProcessTransactionRepository.class);
        ProcessRepository processRepository = mock(ProcessRepository.class);
        MessageProducer messageProducer = mock(MessageProducer.class);
        ArgumentCaptor<ProcessTransaction> argumentCaptor = ArgumentCaptor.forClass(ProcessTransaction.class);

        ProcessFactory processFactory = new ProcessFactory(processTransactionRepository, processRepository);
        Process createdProcess = processFactory.create(transaction, messageProducer);

        verify(processTransactionRepository).save(argumentCaptor.capture());
        ProcessTransaction captured = argumentCaptor.getValue();

        assertEquals(createdProcess.uuid(), captured.getUuid());
    }


    @Test
    void transactionRequestCreated() {
        ProcessTransactionRepository processTransactionRepository = mock(ProcessTransactionRepository.class);
        ProcessRepository processRepository = mock(ProcessRepository.class);
        MessageProducer messageProducer = new MessageProducerSpy();

        ProcessFactory processFactory = new ProcessFactory(processTransactionRepository, processRepository);
        Process createdProcess = processFactory.create(transaction, messageProducer);

        UUID sentUuid = WhiteboxImpl.getInternalState(messageProducer, "receivedUuid");
        TransactionType sentType = WhiteboxImpl.getInternalState(messageProducer, "receivedType");

        assertEquals(createdProcess.uuid(), sentUuid);
        assertEquals(TransactionType.DEPOSIT, sentType);
    }

    private class MessageProducerSpy implements MessageProducer {

        private UUID receivedUuid;

        private TransactionType receivedType;

        @Override
        public void send(ProcessRequest data) {
            receivedUuid = data.uuid();
            receivedType = data.type();
        }
    }
}