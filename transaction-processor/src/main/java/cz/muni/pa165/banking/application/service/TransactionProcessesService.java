package cz.muni.pa165.banking.application.service;

import cz.muni.pa165.banking.application.messaging.ProcessProducer;
import cz.muni.pa165.banking.domain.process.Process;
import cz.muni.pa165.banking.domain.process.ProcessFactory;
import cz.muni.pa165.banking.domain.process.ProcessTransaction;
import cz.muni.pa165.banking.domain.process.repository.ProcessRepository;
import cz.muni.pa165.banking.domain.transaction.Transaction;
import cz.muni.pa165.banking.domain.process.repository.ProcessTransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class TransactionProcessesService {
    
    private final ProcessTransactionRepository processTransactionRepository;

    private final ProcessRepository processRepository;
    
    private final ProcessProducer processProducer;

    public TransactionProcessesService(ProcessTransactionRepository processTransactionRepository, ProcessRepository processRepository, ProcessProducer processProducer) {
        this.processTransactionRepository = processTransactionRepository;
        this.processRepository = processRepository;
        this.processProducer = processProducer;
    }

    @Transactional(rollbackFor = Exception.class)
    public Process createProcessForTransaction(Transaction newTransaction) {
        ProcessFactory factory = new ProcessFactory(processTransactionRepository, processRepository);
        return factory.create(newTransaction, processProducer);
    }

    @Transactional(readOnly = true)
    public Process findProcess(UUID uuid) {
        return processRepository.findById(uuid);
    }
    
    @Transactional(readOnly = true)
    public ProcessTransaction findProcessTransaction(UUID uuid) {
        return processTransactionRepository.findTransactionByProcessId(uuid);
    }
    
    @Transactional(rollbackFor = Exception.class)
    public void revertProcess(UUID uuid) {
        
    }
    
}
