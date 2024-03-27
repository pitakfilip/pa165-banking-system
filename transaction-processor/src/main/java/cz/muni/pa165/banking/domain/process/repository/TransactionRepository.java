package cz.muni.pa165.banking.domain.process.repository;

import cz.muni.pa165.banking.domain.process.ProcessTransaction;
import cz.muni.pa165.banking.domain.transaction.Transaction;

public interface TransactionRepository {
    
    Transaction findTransactionByProcessId(String uuid);
    
    void save(ProcessTransaction transaction);
    
}
