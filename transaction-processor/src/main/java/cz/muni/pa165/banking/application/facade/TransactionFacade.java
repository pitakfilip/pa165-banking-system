package cz.muni.pa165.banking.application.facade;

import cz.muni.pa165.banking.application.mapper.DtoMapper;
import cz.muni.pa165.banking.application.service.TransactionProcessesService;
import cz.muni.pa165.banking.domain.process.Process;
import cz.muni.pa165.banking.domain.process.ProcessTransaction;
import cz.muni.pa165.banking.domain.transaction.Transaction;
import cz.muni.pa165.banking.transaction.processor.dto.ProcessDetailDto;
import cz.muni.pa165.banking.transaction.processor.dto.ProcessDto;
import cz.muni.pa165.banking.transaction.processor.dto.TransactionDto;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TransactionFacade {

    private final TransactionProcessesService service;
    
    private final DtoMapper mapper;

    public TransactionFacade(TransactionProcessesService service, DtoMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    public ProcessDto createTransactionProcess(TransactionDto transactionDto) {
        Transaction transaction = mapper.map(transactionDto);
        Process result = service.createProcessForTransaction(transaction);
     
        return mapper.map(result);
    }
    
    public ProcessDetailDto getStatus(UUID uuid) {
        Process process = service.findProcess(uuid);
        ProcessTransaction processTransaction = service.findProcessTransaction(uuid);
        
        return mapper.map(process, processTransaction);
    }
    
    public void revertProcess(UUID uuid) {
        service.revertProcess(uuid);
    }

}
