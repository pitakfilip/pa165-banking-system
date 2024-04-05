package cz.muni.pa165.banking.application.controller;

import cz.muni.pa165.banking.application.facade.TransactionFacade;
import cz.muni.pa165.banking.transaction.processor.TransactionApi;
import cz.muni.pa165.banking.transaction.processor.dto.ProcessDetailDto;
import cz.muni.pa165.banking.transaction.processor.dto.ProcessDto;
import cz.muni.pa165.banking.transaction.processor.dto.TransactionDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class TransactionController implements TransactionApi {
    
    private final TransactionFacade facade;

    public TransactionController(TransactionFacade facade) {
        this.facade = facade;
    }


    @Override
    public ResponseEntity<ProcessDto> createTransactionProcess(TransactionDto transactionDto) {
        ProcessDto result = facade.createTransactionProcess(transactionDto);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<ProcessDetailDto> status(UUID xProcessUuid) {
        ProcessDetailDto result = facade.getStatus(xProcessUuid);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<ProcessDto> revertTransactionProcess(UUID xProcessUuid) {
        ProcessDto revertingProcess = facade.revertProcess(xProcessUuid);
        return ResponseEntity.ok(revertingProcess);
    }

}
