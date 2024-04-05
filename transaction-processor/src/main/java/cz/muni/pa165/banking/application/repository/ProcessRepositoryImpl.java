package cz.muni.pa165.banking.application.repository;

import cz.muni.pa165.banking.domain.process.Process;
import cz.muni.pa165.banking.domain.process.repository.ProcessRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class ProcessRepositoryImpl implements ProcessRepository {
    
    // TODO until app has no DB connection -> Milestone2
    private final Map<UUID, Process> inmemoryDb = new HashMap<>();

    @Override
    public boolean idExists(UUID uuid) {
        return inmemoryDb.containsKey(uuid);
    }

    @Override
    public Process findById(UUID uuid) {
        return inmemoryDb.get(uuid);
    }

    @Override
    public void save(Process process) {
        inmemoryDb.put(process.uuid(), process);
    }

}
