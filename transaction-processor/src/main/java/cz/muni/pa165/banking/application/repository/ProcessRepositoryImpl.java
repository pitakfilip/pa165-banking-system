package cz.muni.pa165.banking.application.repository;

import cz.muni.pa165.banking.domain.process.Process;
import cz.muni.pa165.banking.domain.process.repository.ProcessRepository;
import cz.muni.pa165.banking.domain.process.status.Status;
import cz.muni.pa165.banking.exception.EntityNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ProcessRepositoryImpl implements ProcessRepository {
    
    private final ProcessRepositoryJpa repository;

    public ProcessRepositoryImpl(ProcessRepositoryJpa repository) {
        this.repository = repository;
    }

    @Override
    public boolean idExists(UUID uuid) {
        return repository.existsById(uuid.toString());
    }

    @Override
    public Process findById(UUID uuid) {
        Optional<Process> process = repository.findById(uuid.toString());
        if (process.isEmpty()) {
            throw new EntityNotFoundException(String.format("Process with UUID %s not found", uuid));
        }
        return process.get();
    }

    @Override
    public void save(Process process) {
        repository.save(process);
    }

    @Override
    public List<Process> findProcessOfStatus(Status status) {
        return null;
    }

    @Override
    public Integer invalidateStaleProcesses() {
        return null;
    }

}
