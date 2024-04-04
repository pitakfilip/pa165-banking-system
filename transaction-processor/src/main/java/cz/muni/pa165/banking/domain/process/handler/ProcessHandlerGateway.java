package cz.muni.pa165.banking.domain.process.handler;

import cz.muni.pa165.banking.domain.process.repository.HandlerMBeanRepository;
import cz.muni.pa165.banking.domain.process.repository.ProcessRepository;
import cz.muni.pa165.banking.domain.transaction.TransactionType;
import cz.muni.pa165.banking.exception.EntityNotFoundException;

import java.util.UUID;

public class ProcessHandlerGateway {

    public void handle(UUID processUuid, TransactionType type, ProcessRepository repository, HandlerMBeanRepository beans) {
        if (!repository.idExists(processUuid.toString())) {
            throw new EntityNotFoundException(String.format("Process with uuid {%s} not found", processUuid));
        }
        
        ProcessHandler handler = switch (type) {
            case WITHDRAW -> new WithdrawHandler(repository);
            case DEPOSIT -> new DepositHandler(repository);
            case CROSS_ACCOUNT -> new CrossAccountHandler(repository);
            case SCHEDULED -> new ScheduledHandler(repository);
        };
        
        handler.handle(processUuid, beans);
    }
    
}
