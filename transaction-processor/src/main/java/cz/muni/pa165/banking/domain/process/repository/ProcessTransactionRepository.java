package cz.muni.pa165.banking.domain.process.repository;

import cz.muni.pa165.banking.domain.process.ProcessTransaction;

import java.util.UUID;

public interface ProcessTransactionRepository {
    
    ProcessTransaction findTransactionByProcessId(UUID processUuid);
    
    void save(ProcessTransaction transaction);
    
}
