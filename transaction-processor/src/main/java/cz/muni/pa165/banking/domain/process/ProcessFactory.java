package cz.muni.pa165.banking.domain.process;

import cz.muni.pa165.banking.domain.messaging.MessageProducer;
import cz.muni.pa165.banking.domain.messaging.ProcessRequest;
import cz.muni.pa165.banking.domain.process.repository.ProcessRepository;
import cz.muni.pa165.banking.domain.transaction.Transaction;
import cz.muni.pa165.banking.domain.process.repository.ProcessTransactionRepository;

/**
 * Factory for Process, which creates a process and also sends it via messaging for further processing.
 */
public class ProcessFactory {
    
    private final ProcessTransactionRepository processTransactionRepository;
    
    private final ProcessRepository processRepository;

    public ProcessFactory(ProcessTransactionRepository processTransactionRepository,
                          ProcessRepository processRepository) {
        this.processTransactionRepository = processTransactionRepository;
        this.processRepository = processRepository;
    }


    public Process create(Transaction transaction, MessageProducer messageProducer) {
        Process newProcess = new Process();
        processRepository.save(newProcess);

        ProcessTransaction assignedTransaction = new ProcessTransaction(
                transaction.getSource(),
                transaction.getTarget(),
                transaction.getType(),
                transaction.getMoney(),
                transaction.getDetail(),
                newProcess.getUuid()
        );
        processTransactionRepository.save(assignedTransaction);
        
        messageProducer.send(new ProcessRequest(newProcess.getUuid(), transaction.getType()));
        
        return newProcess;
    }

    
}
