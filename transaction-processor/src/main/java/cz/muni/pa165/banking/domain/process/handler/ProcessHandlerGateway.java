package cz.muni.pa165.banking.domain.process.handler;

import cz.muni.pa165.banking.domain.process.repository.ProcessRepository;
import cz.muni.pa165.banking.domain.transaction.TransactionType;

public class ProcessHandlerGateway {
    
    private final ProcessRepository processRepository;
    
    public ProcessHandlerGateway(ProcessRepository processRepository) {

        this.processRepository = processRepository;
    }
    
    public void handle(TransactionType type, String uuid) {
        
    }
    
}
