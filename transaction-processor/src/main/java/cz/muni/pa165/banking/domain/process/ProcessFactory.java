package cz.muni.pa165.banking.domain.process;

import cz.muni.pa165.banking.domain.process.repository.ProcessRepository;
import cz.muni.pa165.banking.domain.transaction.Transaction;
import cz.muni.pa165.banking.domain.process.repository.TransactionRepository;

public class ProcessFactory {
    
    private final TransactionRepository transactionRepository;
    
    private final ProcessRepository processRepository;

    public ProcessFactory(TransactionRepository transactionRepository,
                          ProcessRepository processRepository) {
        this.transactionRepository = transactionRepository;
        this.processRepository = processRepository;
    }


    public Process create(Transaction transaction) {
        Process newProcess = new Process();
        processRepository.save(newProcess);

        ProcessTransaction assignedTransaction = new ProcessTransaction(
                transaction.getSource(),
                transaction.getTarget(),
                transaction.getAmount(),
                transaction.getDetail(),
                newProcess.uuid()
        );
        transactionRepository.save(assignedTransaction);
        
        // TODO create a message and publish it to RabbitMq -> asynchronous processing of request
        
        return newProcess;
    }
    
}
