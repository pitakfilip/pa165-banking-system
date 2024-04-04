package cz.muni.pa165.banking.application.service;

import cz.muni.pa165.banking.application.messaging.ProcessProducer;
import cz.muni.pa165.banking.domain.process.Process;
import cz.muni.pa165.banking.domain.process.ProcessFactory;
import cz.muni.pa165.banking.domain.process.repository.ProcessRepository;
import cz.muni.pa165.banking.domain.transaction.Transaction;
import cz.muni.pa165.banking.domain.process.repository.ProcessTransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService {
    
    private final ProcessTransactionRepository processTransactionRepository;

    private final ProcessRepository processRepository;
    
    private final ProcessProducer processProducer;

    public TransactionService(ProcessTransactionRepository processTransactionRepository, ProcessRepository processRepository, ProcessProducer processProducer) {
        this.processTransactionRepository = processTransactionRepository;
        this.processRepository = processRepository;
        this.processProducer = processProducer;
    }

    @Transactional(rollbackFor = Exception.class)
    public Process createProcessForTransaction(Transaction newTransaction) {
        ProcessFactory factory = new ProcessFactory(processTransactionRepository, processRepository);
        return factory.create(newTransaction, processProducer);
    }

}
