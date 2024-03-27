package cz.muni.pa165.banking.application.controller;

import cz.muni.pa165.banking.application.facade.TransactionFacade;
import cz.muni.pa165.banking.transaction.processor.TransactionApi;
import cz.muni.pa165.banking.transaction.processor.dto.ProcessDto;
import cz.muni.pa165.banking.transaction.processor.dto.TransactionDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransactionController implements TransactionApi {
    
    private final TransactionFacade facade;

    public TransactionController(TransactionFacade facade) {
        this.facade = facade;
    }


    @Override
    public ResponseEntity<ProcessDto> createTransactionProcess(TransactionDto transactionDto) {
        ProcessDto result = facade.createTransactionProcess(transactionDto);
        
        
        return null;
    }
}
