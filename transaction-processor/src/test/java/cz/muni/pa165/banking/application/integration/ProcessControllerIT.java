package cz.muni.pa165.banking.application.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.muni.pa165.banking.application.proxy.rate.ExchangeRatesApi;
import cz.muni.pa165.banking.application.repository.ProcessRepositoryJpa;
import cz.muni.pa165.banking.application.service.ProcessService;
import cz.muni.pa165.banking.domain.process.Process;
import cz.muni.pa165.banking.domain.process.ProcessOperations;
import cz.muni.pa165.banking.domain.process.status.Status;
import cz.muni.pa165.banking.domain.process.status.StatusInformation;
import cz.muni.pa165.banking.transaction.processor.dto.ProcessStatusDto;
import cz.muni.pa165.banking.transaction.processor.dto.ProcessStatusListDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureDataJpa
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class ProcessControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProcessService service;

    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private ProcessRepositoryJpa repository;

    @MockBean
    private ExchangeRatesApi apiMock;
    
    @MockBean
    private SecurityFilterChain securityFilterChainMock;
    
    private static final Instant instant = LocalDateTime.of(2024, Month.APRIL, 24, 12, 0, 0).toInstant(ZoneOffset.UTC);

    private static Set<Process> storedProcesses;
    
    private static Process processedProcess;
    
    
    @BeforeAll
    public static void initDb(@Autowired ProcessRepositoryJpa repository) {
        repository.save(Process.createNew());
        repository.save(Process.createNew());

        Process process1 = Process.createNew();
        ProcessOperations.changeState(process1, new StatusInformation(instant, Status.PENDING, "PENDING"));
        repository.save(process1);

        Process process2 = Process.createNew();
        ProcessOperations.changeState(process2, new StatusInformation(instant, Status.PENDING, "PENDING"));
        repository.save(process2);

        LocalDateTime currentDateTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
        LocalDateTime newDateTime = currentDateTime.minusDays(10);
        Instant tenDaysAgo = newDateTime.toInstant(ZoneOffset.UTC);

        processedProcess = Process.createNew();
        ProcessOperations.changeState(processedProcess, new StatusInformation(tenDaysAgo, Status.PROCESSED, "PROCESSED"));
        repository.save(processedProcess);

        Process process4 = Process.createNew();
        ProcessOperations.changeState(process4, new StatusInformation(tenDaysAgo, Status.CREATED, "CREATED"));
        repository.save(process4);

        Process process5 = Process.createNew();
        ProcessOperations.changeState(process5, new StatusInformation(tenDaysAgo, Status.PENDING, "PENDING"));
        repository.save(process5);

        storedProcesses = Set.of(process1, process2, processedProcess, process4, process5);
    }

    @Test
    @WithMockUser(authorities = "SCOPE_test_2")
    public void resolveStaleProcesses() throws Exception {
        LocalDateTime currentDateTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
        LocalDateTime newDateTime = currentDateTime.minusDays(1);
        LocalDate yesterday = newDateTime.toLocalDate();

        List<Process> staleProcesses = service.unresolvedProcessesToDate(yesterday);
        for (Process process : staleProcesses) {
            assertNotEquals(Status.FAILED, process.getStatus());
            assertNotEquals(Status.PROCESSED, process.getStatus());
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

    @Test
    @WithMockUser(authorities = "SCOPE_test_2")
    public void fetchProcessedProcesses() throws Exception {
        Set<UUID> expectedProcessIds = storedProcesses.stream()
                .filter(p -> p.getStatus().equals(Status.PROCESSED))
                .map(Process::getUuid)
                .collect(Collectors.toSet());

        MvcResult response = mockMvc.perform(get("/processes/v1/PROCESSED")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = response.getResponse().getContentAsString();
        List<ProcessStatusDto> foundProcesses = objectMapper.readValue(jsonResponse, ProcessStatusListDto.class).getProcesses();

        assertEquals(expectedProcessIds.size(), foundProcesses.size());

        for (ProcessStatusDto process : foundProcesses) {
            assertEquals(process.getStatus().getStatus().getValue(), "PROCESSED");
            assertTrue(expectedProcessIds.contains(process.getIdentifier()));
        }
    }

    @Test
    @WithMockUser(authorities = "SCOPE_test_2")
    public void fetchValidProcessStatus() throws Exception {
        MvcResult response = mockMvc.perform(get("/process/v1/{uuid}/status", processedProcess.getUuid())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = response.getResponse().getContentAsString();
        ProcessStatusDto status = objectMapper.readValue(jsonResponse, ProcessStatusDto.class);
        
        assertEquals(processedProcess.getUuid(), status.getIdentifier());
        assertEquals(processedProcess.getStatus().name(), status.getStatus().getStatus().getValue());
    }
    
    @Test
    @WithMockUser(authorities = "SCOPE_test_2")
    public void fetchInvalidProcessStatus() throws Exception {
        MvcResult response = mockMvc.perform(get("/process/v1/{uuid}/status", UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        String jsonResponse = response.getResponse().getContentAsString();
        LinkedHashMap JSON = objectMapper.readValue(jsonResponse, LinkedHashMap.class);
        
        assertTrue(JSON.containsKey("status"));
        assertEquals("NOT_FOUND", JSON.get("status"));
        assertTrue(JSON.containsKey("message"));
        assertEquals("Entity not present in repository", JSON.get("message"));
    }

}

