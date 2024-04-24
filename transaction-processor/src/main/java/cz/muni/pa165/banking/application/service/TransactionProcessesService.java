package cz.muni.pa165.banking.application.service;

import cz.muni.pa165.banking.domain.messaging.MessageProducer;
import cz.muni.pa165.banking.domain.process.Process;
import cz.muni.pa165.banking.domain.process.ProcessFactory;
import cz.muni.pa165.banking.domain.process.ProcessTransaction;
import cz.muni.pa165.banking.domain.process.repository.ProcessRepository;
import cz.muni.pa165.banking.domain.process.status.Status;
import cz.muni.pa165.banking.domain.transaction.Transaction;
import cz.muni.pa165.banking.domain.process.repository.ProcessTransactionRepository;
import cz.muni.pa165.banking.domain.transaction.TransactionType;
import cz.muni.pa165.banking.exception.UnexpectedValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class TransactionProcessesService {
    
    private final Logger LOGGER = LoggerFactory.getLogger(TransactionProcessesService.class);
    
    private final ProcessTransactionRepository processTransactionRepository;

    private final ProcessRepository processRepository;
    
    private final MessageProducer processProducer;

    public TransactionProcessesService(ProcessTransactionRepository processTransactionRepository,
                                       ProcessRepository processRepository,
                                       MessageProducer processProducer) {
        this.processTransactionRepository = processTransactionRepository;
        this.processRepository = processRepository;
        this.processProducer = processProducer;
    }

    @Transactional(rollbackFor = Exception.class)
    public Process createProcessForTransaction(Transaction newTransaction) {
        ProcessFactory factory = new ProcessFactory(processTransactionRepository, processRepository);
        Process process = factory.create(newTransaction, processProducer);
        
        LOGGER.info("[Create Process] %s" + process.getUuid());
        
        return process;
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
    public Process revertProcess(UUID uuid) {
        Process process = processRepository.findById(uuid);
        
        if (!process.getStatus().equals(Status.PROCESSED)) {
            LOGGER.error("[Revert Process] Process " + uuid + " not finalized successfully, unable to revert");
            throw new UnexpectedValueException(
                    "Unable to revert process, which is not successfully processed!",
                    process.toString()
            );
        }
        
        ProcessTransaction processTransaction = processTransactionRepository.findTransactionByProcessId(uuid);
        
        if (!processTransaction.getType().equals(TransactionType.TRANSFER) || processTransaction.getType().equals(TransactionType.SCHEDULED)) {
            LOGGER.error("[Revert Process] Process " + uuid + " not of type TRANSFER/SCHEDULED, unable to revert");
            throw new UnexpectedValueException("Unable to revert transaction not type of TRANSFER or SCHEDULED!");
        }

        Transaction revertingTransaction = new Transaction(
                processTransaction.getTarget(),
                processTransaction.getSource(),
                TransactionType.REFUND,
                processTransaction.getMoney(),
                String.format("Admin reversal of executed %s transaction {%s}", processTransaction.getType(), uuid)
        );
        
        ProcessFactory factory = new ProcessFactory(processTransactionRepository, processRepository);
        Process revertingProcess = factory.create(revertingTransaction, processProducer);

        LOGGER.info(String.format("[Revert Process] Created new process {%s} in order to revert process {%s}", revertingProcess.getUuid(), uuid));   
        
        return revertingProcess;
    }
    
}
