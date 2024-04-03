package cz.muni.pa165.banking.domain.process.handler;

import cz.muni.pa165.banking.domain.process.Process;
import cz.muni.pa165.banking.domain.process.ProcessOperations;
import cz.muni.pa165.banking.domain.process.repository.HandlerMBeanRepository;
import cz.muni.pa165.banking.domain.process.repository.ProcessRepository;
import cz.muni.pa165.banking.domain.process.status.Status;
import cz.muni.pa165.banking.domain.process.status.StatusInformation;

import java.time.Instant;
import java.util.UUID;

abstract class ProcessHandler {

    protected final ProcessRepository processRepository;

    ProcessHandler(ProcessRepository processRepository) {
        this.processRepository = processRepository;
    }

    final void handle(UUID processUuid, HandlerMBeanRepository beans) {
        Process process = processRepository.findById(processUuid.toString());
        validateProcess(process);
        
        Status newStatus = Status.PROCESSED;
        String message = "Deposit finalized";
        try {
            evaluate(process, beans);
        } catch (Exception e) {
            newStatus = Status.FAILED;
            message = e.getMessage();
        }
        
        ProcessOperations.changeState(process, new StatusInformation(Instant.now(), newStatus, message));
        processRepository.save(process);
    }

    abstract void evaluate(Process process, HandlerMBeanRepository beans);

    private void validateProcess(Process process) {
        if (process.getStatus().equals(Status.FAILED)) {
            throw new RuntimeException("Process already finalized, ended with failure: " + process.getStatusInformation());
        }
        if (process.getStatus().equals(Status.PROCESSED)) {
            throw new RuntimeException("Process already finalized");
        }
    }

}
