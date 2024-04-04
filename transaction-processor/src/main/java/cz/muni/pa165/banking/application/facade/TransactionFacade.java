package cz.muni.pa165.banking.application.facade;

import cz.muni.pa165.banking.application.mapper.DtoMapper;
import cz.muni.pa165.banking.application.service.TransactionService;
import cz.muni.pa165.banking.domain.process.Process;
import cz.muni.pa165.banking.domain.transaction.Transaction;
import cz.muni.pa165.banking.exception.EntityNotFoundException;
import cz.muni.pa165.banking.transaction.processor.dto.ProcessDto;
import cz.muni.pa165.banking.transaction.processor.dto.TransactionDto;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
public class TransactionFacade {

    private final TransactionService service;
    
    private final DtoMapper mapper;

    public TransactionFacade(TransactionService service, DtoMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    public ProcessDto createTransactionProcess(TransactionDto transactionDto) {
        Transaction transaction = mapper.map(transactionDto);
        Process result = service.createProcessForTransaction(transaction);
        return mapper.map(result);
        
        // call service to create process, validate stuff etc. etc.
        
        // check if all validations passed (accounts exist, enough balance on source account, ...)
        
        // call service to start 'sending money' to target account, would contain:
        //      - fetch source account info about currency
        //      - fetch target account info about currency
        //      - if needed, convert money via CurrencyConverter
        //      - process 
        
        // call messaging to push new value finalized process into query module (updates balance of both accounts, saves changes)
    }
    
}
