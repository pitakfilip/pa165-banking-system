package cz.muni.pa165.banking.domain.process.handler;

import cz.muni.pa165.banking.domain.process.Process;
import cz.muni.pa165.banking.domain.process.repository.ProcessRepository;
import cz.muni.pa165.banking.domain.transaction.TransactionType;

class DepositHandler implements ProcessHandler<TransactionType> {
    
    private final ProcessRepository repository;
    
    DepositHandler(ProcessRepository repository) {
        this.repository = repository;
    }

                   @Override
    public void handle(String uuid) {
        Process process = repository.findById(uuid);
        validate(process);
        
        // TODO
    }
}
