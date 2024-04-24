package cz.muni.pa165.banking.domain.process;

import cz.muni.pa165.banking.domain.process.status.Status;
import cz.muni.pa165.banking.domain.process.status.StatusInformation;

import java.time.Instant;
import java.util.UUID;

public class ProcessMock extends Process {
    
    private final UUID uuid;
    
    public ProcessMock() {
        super();
        this.uuid = UUID.randomUUID();
        this.setCurrentStatus(new StatusInformation(Instant.now(), Status.CREATED, "Process created, waiting for processing."));
    }
    
    @Override
    public UUID getUuid() {
        return this.uuid;
    }
}
