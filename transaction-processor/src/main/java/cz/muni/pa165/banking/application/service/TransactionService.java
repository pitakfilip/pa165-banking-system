package cz.muni.pa165.banking.application.service;

import cz.muni.pa165.banking.domain.process.Process;
import cz.muni.pa165.banking.domain.process.ProcessFactory;
import cz.muni.pa165.banking.domain.process.repository.ProcessRepository;
import cz.muni.pa165.banking.domain.transaction.Transaction;
import cz.muni.pa165.banking.domain.process.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService {
    
    private final TransactionRepository transactionRepository;

    private final ProcessRepository processRepository;

    public TransactionService(TransactionRepository transactionRepository, ProcessRepository processRepository) {
        this.transactionRepository = transactionRepository;
        this.processRepository = processRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public Process createProcessForTransaction(Transaction newTransaction) {
        ProcessFactory factory = new ProcessFactory(transactionRepository, processRepository);
        return factory.create(newTransaction);
    }
    
}
