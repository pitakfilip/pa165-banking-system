package cz.muni.pa165.banking.application.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.muni.pa165.banking.application.repository.ProcessRepositoryJpa;
import cz.muni.pa165.banking.application.service.ProcessService;
import cz.muni.pa165.banking.domain.process.Process;
import cz.muni.pa165.banking.domain.process.ProcessOperations;
import cz.muni.pa165.banking.domain.process.status.Status;
import cz.muni.pa165.banking.domain.process.status.StatusInformation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.*;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataJpa
public class ProcessControllerIT {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ProcessService service;
    
    @Autowired
    private ObjectMapper objectMapper;

    private static final Instant instant = LocalDateTime.of(2024, Month.APRIL, 24, 12, 0, 0).toInstant(ZoneOffset.UTC);
    
    @BeforeAll
    public static void initDb(@Autowired ProcessRepositoryJpa repository) {
        repository.save(Process.createNew());
        repository.save(Process.createNew());

        Process processed1 = Process.createNew();
        ProcessOperations.changeState(processed1, new StatusInformation(instant, Status.PENDING, "PENDING"));
        repository.save(processed1);

        Process processed2 = Process.createNew();
        ProcessOperations.changeState(processed2, new StatusInformation(instant, Status.PENDING, "PENDING"));
        repository.save(processed2);

        LocalDateTime currentDateTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
        LocalDateTime newDateTime = currentDateTime.minusDays(10);
        Instant tenDaysAgo = newDateTime.toInstant(ZoneOffset.UTC);

        Process processed4 = Process.createNew();
        ProcessOperations.changeState(processed4, new StatusInformation(tenDaysAgo, Status.CREATED, "CREATED"));
        repository.save(processed4);

        Process processed5 = Process.createNew();
        ProcessOperations.changeState(processed5, new StatusInformation(tenDaysAgo, Status.PENDING, "PENDING"));
        repository.save(processed5);
    }

    @Test
    public void resolveStaleProcesses() throws Exception {
        LocalDateTime currentDateTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
        LocalDateTime newDateTime = currentDateTime.minusDays(1);
        LocalDate yesterday = newDateTime.toLocalDate();
        
        List<Process> staleProcesses = service.unresolvedProcessesToDate(yesterday);
        for (Process process : staleProcesses) {
            assertNotEquals(Status.FAILED, process.getStatus());
        }
        List<UUID> staleProcessIds = staleProcesses.stream()
                .map(Process::getUuid)
                .toList();

        MvcResult response = mockMvc.perform(patch("/processes/v1/resolve")
                        .param("date", yesterday.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        
        String jsonResponse = response.getResponse().getContentAsString();
        List<UUID> resolvedProcessIds = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
        assertEquals(staleProcessIds.size(), resolvedProcessIds.size());
        assertTrue(resolvedProcessIds.containsAll(staleProcessIds));
        
        for (UUID uuid : resolvedProcessIds) {
            Process process = service.findByUuid(uuid);
            assertEquals(Status.FAILED, process.getStatus());
            assertEquals("Resolved stale process as FAILED by system", process.getInformation());
        }
    }
    
}
