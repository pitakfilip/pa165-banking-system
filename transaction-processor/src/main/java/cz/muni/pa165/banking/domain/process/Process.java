package cz.muni.pa165.banking.domain.process;

import cz.muni.pa165.banking.domain.process.status.Status;
import cz.muni.pa165.banking.domain.process.status.StatusInformation;
import jakarta.persistence.Entity;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Process {
    
    private UUID uuid;
    
    private StatusInformation currentStatus;
    
    public static Process createNew() {
        Process process = new Process();
        process.uuid = UUID.randomUUID();
        process.currentStatus = new StatusInformation(Instant.now(), Status.CREATED, "Process created, waiting for processing.");
        
        return process;
    }

    @Deprecated // hibernate
    public Process() {}

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Process process = (Process) o;
        return Objects.equals(getUuid(), process.getUuid()) && Objects.equals(getCurrentStatus(), process.getCurrentStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUuid(), getCurrentStatus());
    }

    @Override
    public String toString() {
        return "Process{" +
                "uuid=" + uuid +
                ", currentStatus=" + currentStatus +
                '}';
    }
}
