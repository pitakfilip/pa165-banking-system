package cz.muni.pa165.banking.application.repository;

import cz.muni.pa165.banking.domain.process.Process;
import cz.muni.pa165.banking.domain.process.repository.ProcessRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ProcessRepositoryImpl implements ProcessRepository {
    
    // until app has no DB connection
    private final Map<String, Process> inmemoryDb = new HashMap<>();

    @Override
    public Process findById(String uuid) {
        return inmemoryDb.get(uuid);
    }

    @Override
    public void save(Process process) {
        inmemoryDb.put(process.uuid(), process);
    }

}
