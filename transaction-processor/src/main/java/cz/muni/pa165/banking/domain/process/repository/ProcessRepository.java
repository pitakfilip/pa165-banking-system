package cz.muni.pa165.banking.domain.process.repository;

import cz.muni.pa165.banking.domain.process.Process;

public interface ProcessRepository {
    
    Process findById(String uuid);
    
    void save(Process process);
    
}
