package cz.muni.pa165.banking.application.service;


import cz.muni.pa165.banking.domain.process.Process;
import cz.muni.pa165.banking.domain.process.repository.ProcessRepository;
import cz.muni.pa165.banking.domain.process.status.Status;
import cz.muni.pa165.banking.exception.UnexpectedValueException;
import org.awaitility.reflect.WhiteboxImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProcessServiceTest {

    @Mock
    private ProcessRepository processRepository;

    @InjectMocks
    private ProcessService processService;

    @Test
    void findByUuid_shouldCallProcessRepository() {
        UUID uuid = UUID.randomUUID();

        processService.findByUuid(uuid);

        verify(processRepository, times(1)).findById(uuid);
    }

    @Test
    void processesWithStatus_shouldCallProcessRepository() {
        Status status = Status.CREATED;

        processService.processesWithStatus(status);

        verify(processRepository, times(1)).findProcessOfStatus(status);
    }

    @Test
    void unresolvedProcessesToDate_shouldCallProcessRepository() {
        LocalDate threshold = LocalDate.now();

        processService.unresolvedProcessesToDate(threshold);

        verify(processRepository, times(1)).findProcessesOfStatusUptoDate(Status.CREATED, threshold);
        verify(processRepository, times(1)).findProcessesOfStatusUptoDate(Status.PENDING, threshold);
    }

    @Test
    void resolveProcesses_failOnInvalidThreshold() {
        LocalDate localDate = LocalDate.now();

        UnexpectedValueException e = assertThrows(
                UnexpectedValueException.class,
                () -> processService.resolveProcesses(localDate)
        );
        
        String cause = WhiteboxImpl.getInternalState(e, "cause");
        assertEquals("Threshold should be atleast one day ago. Cannot resolve processes from today or the future", cause);
    }

    @Test
    void resolveProcesses_shouldCallProcessRepository() {
        LocalDate localDate = LocalDate.now().minusDays(2);
        
        Process p1 = Process.createNew();
        Process p2 = Process.createNew();
        when(processRepository.findProcessesOfStatusUptoDate(Status.CREATED, localDate)).thenReturn(List.of(p1));
        when(processRepository.findProcessesOfStatusUptoDate(Status.PENDING, localDate)).thenReturn(List.of(p2));
        
        processService.resolveProcesses(localDate);

        verify(processRepository, times(2)).findProcessesOfStatusUptoDate(any(), any());
        verify(processRepository, times(1)).save(p1);
        verify(processRepository, times(1)).save(p2);
    }
}