package cz.muni.pa165.banking.application.service;

import cz.muni.pa165.banking.domain.process.Process;
import cz.muni.pa165.banking.domain.process.ProcessOperations;
import cz.muni.pa165.banking.domain.process.repository.ProcessRepository;
import cz.muni.pa165.banking.domain.process.status.Status;
import cz.muni.pa165.banking.domain.process.status.StatusInformation;
import cz.muni.pa165.banking.exception.UnexpectedValueException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProcessService {
    
    private final ProcessRepository processRepository;

    public ProcessService(ProcessRepository processRepository) {
        this.processRepository = processRepository;
    }
    
    public Process findByUuid(UUID uuid) {
        return processRepository.findById(uuid);
    }
    
    @Transactional(readOnly = true)
    public List<Process> processesWithStatus(Status status) {
        return processRepository.findProcessOfStatus(status);
    }

    @Transactional(readOnly = true)
    public List<Process> unresolvedProcessesToDate(LocalDate threshold) {
        List<Process> result = new ArrayList<>();
        
        result.addAll(processRepository.findProcessesOfStatusUptoDate(Status.CREATED, threshold));
        result.addAll(processRepository.findProcessesOfStatusUptoDate(Status.PENDING, threshold));
        
        return result;
    }
    
    @Transactional
    public List<UUID> resolveProcesses(LocalDate localDate) {
        LocalDate now = LocalDate.now();
        if (!localDate.isBefore(now)) {
            throw new UnexpectedValueException("Threshold should be atleast one day ago. Cannot resolve processes from today or the future");
        }
        
        List<Process> staleProcesses = new ArrayList<>();
        staleProcesses.addAll(processRepository.findProcessesOfStatusUptoDate(Status.CREATED, localDate));
        staleProcesses.addAll(processRepository.findProcessesOfStatusUptoDate(Status.PENDING, localDate));

        StatusInformation resolvedInfo = new StatusInformation(Instant.now(), Status.FAILED, "Resolved stale process as FAILED by system");
        
        List<UUID> resolved = new ArrayList<>();
        for (Process process : staleProcesses) {
            try {
                ProcessOperations.changeState(process, resolvedInfo);
                processRepository.save(process);
                resolved.add(process.getUuid());
            } catch (Exception ignored){}
        }
        
        return resolved;
    }
}
