package cz.muni.pa165.banking.domain.process;

import java.time.Instant;

// @Entity
public class Process {
    
    private Instant created;
    
    private int status; // representing current status (created / processing / pending / failed / finished ) -> create an ENUM
    
    // some other info about what the process is about (transfer money account -> account, deposit, ..., source, target, etc.)
    
    
}
