package cz.muni.pa165.banking.domain.process;

import cz.muni.pa165.banking.domain.process.status.Status;
import cz.muni.pa165.banking.domain.process.status.StatusInformation;

import java.time.Instant;
import java.util.UUID;

// @Entity
public class Process {
    
    private final UUID uuid;
    
    private StatusInformation currentStatus;
    
    public Process() {
        uuid = UUID.randomUUID();
        currentStatus = new StatusInformation(Instant.now(), Status.CREATED, "Process created, waiting for processing.");
    }

    /**
     * Return a copy of Process UUID, ensuring the UUID is not modified or replaced. 
     */
    public UUID uuid() {
        return UUID.fromString(uuid.toString());
    }
    
    public Status getStatus() {
        return currentStatus.status();
    }
    
    public String getStatusInformation() {
        return currentStatus.information();
    }
    
    void setCurrentStatus(StatusInformation information) {
        currentStatus = information;
    }
    
}
