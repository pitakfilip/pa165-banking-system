package cz.muni.pa165.banking.domain.process;

import cz.muni.pa165.banking.domain.process.status.Status;
import cz.muni.pa165.banking.domain.process.status.StatusInformation;
import jakarta.persistence.Entity;

import java.time.Instant;
import java.util.UUID;

@Entity
public class Process {
    
    private final UUID uuid;
    
    private StatusInformation currentStatus;
    
    Process() {
        uuid = UUID.randomUUID();
        currentStatus = new StatusInformation(Instant.now(), Status.CREATED, "Process created, waiting for processing.");
    }

    /**
     * Return a copy of Process UUID, ensuring the UUID is not modified or replaced. 
     */
    public UUID getUuid() {
        return UUID.fromString(uuid.toString());
    }
    
    public Instant getWhen() {
        return currentStatus.getWhen();
    }
    
    public Status getStatus() {
        return currentStatus.getStatus();
    }
    
    public String getInformation() {
        return currentStatus.getInformation();
    }

    @Deprecated // hibernate
    public StatusInformation getCurrentStatus() {
        return currentStatus;
    }
    @Deprecated // hibernate
    void setCurrentStatus(StatusInformation information) {
        currentStatus = information;
    }
    
}
