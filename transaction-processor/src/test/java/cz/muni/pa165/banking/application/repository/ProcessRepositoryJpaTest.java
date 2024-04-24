package cz.muni.pa165.banking.application.repository;

import cz.muni.pa165.banking.domain.process.Process;
import cz.muni.pa165.banking.domain.process.ProcessOperations;
import cz.muni.pa165.banking.domain.process.status.Status;
import cz.muni.pa165.banking.domain.process.status.StatusInformation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ProcessRepositoryJpaTest {
    
    @Autowired
    private ProcessRepositoryJpa repository;
    
    private static final Instant instant = LocalDateTime.of(2024, Month.APRIL, 24, 12, 0, 0).toInstant(ZoneOffset.UTC);
    
    
    @BeforeAll
    public static void initDb(@Autowired ProcessRepositoryJpa repository) {
        repository.save(Process.createNew());
        repository.save(Process.createNew());
        repository.save(Process.createNew());
        
        Process processed1 = Process.createNew();
        ProcessOperations.changeState(processed1, new StatusInformation(instant, Status.PROCESSED, "done"));
        repository.save(processed1);
        
        Process processed2 = Process.createNew();
        ProcessOperations.changeState(processed2, new StatusInformation(instant, Status.PROCESSED, "done"));
        repository.save(processed2);
        
        Process processed3 = Process.createNew();
        ProcessOperations.changeState(processed3, new StatusInformation(instant, Status.PROCESSED, "done"));
        repository.save(processed3);

        LocalDateTime currentDateTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
        LocalDateTime newDateTime = currentDateTime.minusDays(1);
        Instant yesterday = newDateTime.toInstant(ZoneOffset.UTC);
        
        Process processed4 = Process.createNew();
        ProcessOperations.changeState(processed4, new StatusInformation(yesterday, Status.PROCESSED, "done"));
        repository.save(processed4);
        
        Process processed5 = Process.createNew();
        ProcessOperations.changeState(processed5, new StatusInformation(yesterday, Status.PROCESSED, "done"));
        repository.save(processed5);
    }
    
    @Test
    public void findAllWithStatusCreated() {
        List<Process> createdProcesses = repository.findAllWithStatus(Status.CREATED);

        assertEquals(3, createdProcesses.size());

        Set<Status> statuses = createdProcesses.stream()
                .map(Process::getStatus)
                .collect(Collectors.toSet());
        assertEquals(1, statuses.size());
        assertTrue(statuses.contains(Status.CREATED));
    }
    
    @Test
    public void findProcessedProcessesFromYesterday() {
        LocalDateTime currentDateTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
        LocalDateTime newDateTime = currentDateTime.minusDays(1);
        Instant yesterday = newDateTime.toInstant(ZoneOffset.UTC);
        
        List<Process> processedYesterday = repository.findByStatusAndDateBeforeEqual(Status.PROCESSED, yesterday);
        assertEquals(2, processedYesterday.size());
    }

    @Test
    public void findCreatedProcessesFromYesterday() {
        LocalDateTime currentDateTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
        LocalDateTime newDateTime = currentDateTime.minusDays(1);
        Instant yesterday = newDateTime.toInstant(ZoneOffset.UTC);

        List<Process> processedYesterday = repository.findByStatusAndDateBeforeEqual(Status.CREATED, yesterday);
        assertEquals(0, processedYesterday.size());
    }
}
