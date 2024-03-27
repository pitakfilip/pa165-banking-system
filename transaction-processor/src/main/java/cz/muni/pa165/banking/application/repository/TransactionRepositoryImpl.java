package cz.muni.pa165.banking.application.repository;

import cz.muni.pa165.banking.domain.process.ProcessTransaction;
import cz.muni.pa165.banking.domain.transaction.Transaction;
import cz.muni.pa165.banking.domain.process.repository.TransactionRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class TransactionRepositoryImpl implements TransactionRepository {
    
    // until app has no DB connection
    private final Map<String, ProcessTransaction> inmemoryDb = new HashMap<>();
    
    @Override
    public Transaction findTransactionByProcessId(String uuid) {
        return inmemoryDb.get(uuid);
    }

    @Override
    public void save(ProcessTransaction transaction) {
        inmemoryDb.put(transaction.getUuid(), transaction);
    }

}
