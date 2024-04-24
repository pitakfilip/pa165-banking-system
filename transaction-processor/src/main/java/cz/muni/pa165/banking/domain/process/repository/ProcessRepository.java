package cz.muni.pa165.banking.domain.process.repository;

import cz.muni.pa165.banking.domain.process.Process;
import cz.muni.pa165.banking.domain.process.status.Status;

import java.util.List;
import java.util.UUID;

public interface ProcessRepository {
    
    boolean idExists(UUID uuid);
    
    Process findById(UUID uuid);
    
    void save(Process process);
    
    List<Process> findProcessOfStatus(Status status);
    
    Integer invalidateStaleProcesses();
}
