package cz.muni.pa165.banking.application.repository;

import cz.muni.pa165.banking.domain.process.ProcessTransaction;
import cz.muni.pa165.banking.domain.process.repository.ProcessTransactionRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class ProcessTransactionRepositoryImpl implements ProcessTransactionRepository {
    
    // until app has no DB connection
    private final Map<UUID, ProcessTransaction> inMemoryDb = new HashMap<>();
    
    @Override
    public ProcessTransaction findTransactionByProcessId(UUID processUuid) {
        return inMemoryDb.get(processUuid);
    }

    @Override
    public void save(ProcessTransaction transaction) {
        inMemoryDb.put(transaction.getUuid(), transaction);
    }

}
