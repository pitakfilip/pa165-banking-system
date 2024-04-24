package cz.muni.pa165.banking.domain.process;

import java.util.UUID;

public class ProcessMock extends Process {
    
    private UUID uuid;
    
    public ProcessMock() {
        super();
        this.uuid = UUID.randomUUID();
    }
    
    @Override
    public UUID getUuid() {
        return this.uuid;
    }
}
