package cz.muni.pa165.banking.domain.process;

import cz.muni.pa165.banking.domain.process.status.Status;
import cz.muni.pa165.banking.domain.process.status.StatusInformation;

import java.time.Instant;
import java.util.UUID;

// @Entity
public class Process {
    
    private UUID uuid;
    
    private StatusInformation currentStatus;
    
    public Process() {
        uuid = UUID.randomUUID();
        currentStatus = new StatusInformation(Instant.now(), Status.CREATED, "Process created, waiting for processing.");
    }

    public String uuid() {
        return uuid.toString();
    }
    
    public Status getStatus() {
        return currentStatus.status();
    }
    
    public String getStatusInformation() {
        return currentStatus.information();
    }
    
}
