package cz.muni.pa165.banking.domain.process.handler;

import cz.muni.pa165.banking.domain.process.repository.ProcessRepository;
import cz.muni.pa165.banking.exception.EntityNotFoundException;
import cz.muni.pa165.banking.exception.UnexpectedValueException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProcessHandlerGatewayTest {

    private static UUID uuid;
    private static ProcessRepository processRepository;
    
    @BeforeAll
    static void setUp() {
        uuid = UUID.randomUUID();
        processRepository = mock(ProcessRepository.class);
        when(processRepository.idExists(uuid)).thenReturn(true);
    }
    
    
    @Test
    void processNotFound() {
        ProcessRepository processRepository = mock(ProcessRepository.class);
        assertThrows(
                EntityNotFoundException.class,
                () -> ProcessHandlerGateway.handle(UUID.randomUUID(), null, processRepository, null, null, null)
        );
    }

    @Test
    void nullTransactionType() {
        assertThrows(
                UnexpectedValueException.class,
                () -> ProcessHandlerGateway.handle(uuid, null, processRepository, null, null, null)
        );
    }
    
}