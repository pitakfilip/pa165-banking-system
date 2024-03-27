package cz.muni.pa165.banking.domain.process.handler;

import cz.muni.pa165.banking.domain.process.Process;
import cz.muni.pa165.banking.domain.process.status.Status;

interface ProcessHandler<T extends Enum<T>> {

    void handle(String uuid);
    
    default void validate(Process process) {
        if (process == null) {
            throw new RuntimeException("Process not found");
        }
        if (process.getStatus().equals(Status.FAILED)) {
            throw new RuntimeException("Process already finalized, ended with failure: " + process.getStatusInformation());
        }
        if (process.getStatus().equals(Status.PROCESSED)) {
            throw new RuntimeException("Process already finalized");
        }
    }
    
}
