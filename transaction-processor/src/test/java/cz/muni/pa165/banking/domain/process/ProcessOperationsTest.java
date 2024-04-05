package cz.muni.pa165.banking.domain.process;

import cz.muni.pa165.banking.domain.process.status.Status;
import cz.muni.pa165.banking.domain.process.status.StatusInformation;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class ProcessOperationsTest {

    @Test
    void changeState() {
        Process process = new Process();

        StatusInformation newStatus = new StatusInformation(
                Instant.now(),
                Status.FAILED,
                "Pat a Mat nieco zase pokazili"
        );
        
        ProcessOperations.changeState(process, newStatus);
        
        assertEquals(newStatus.when(), process.getWhen());
        assertEquals(newStatus.status(), process.getStatus());
        assertEquals(newStatus.information(), process.getStatusInformation());
    }
}