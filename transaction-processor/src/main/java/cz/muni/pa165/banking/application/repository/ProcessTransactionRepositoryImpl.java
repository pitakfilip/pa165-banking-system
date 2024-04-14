package cz.muni.pa165.banking.application.repository;

import cz.muni.pa165.banking.domain.process.ProcessTransaction;
import cz.muni.pa165.banking.domain.process.repository.ProcessTransactionRepository;
import cz.muni.pa165.banking.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class ProcessTransactionRepositoryImpl implements ProcessTransactionRepository {
    
    @Autowired
    private TransactionRepositoryJpa repository;
    
    @Override
    public ProcessTransaction findTransactionByProcessId(UUID processUuid) {
        return repository.findById(processUuid.toString())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Transaction with Process ID %s not found", processUuid)));
    }

    @Override
    public void save(ProcessTransaction transaction) {
        repository.save(transaction);
    }

}
