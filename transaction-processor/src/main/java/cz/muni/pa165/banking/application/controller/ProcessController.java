package cz.muni.pa165.banking.application.controller;

import cz.muni.pa165.banking.application.facade.ProcessFacade;
import cz.muni.pa165.banking.transaction.processor.ProcessApi;
import cz.muni.pa165.banking.transaction.processor.dto.ProcessStatusDto;
import cz.muni.pa165.banking.transaction.processor.dto.ProcessStatusListDto;
import cz.muni.pa165.banking.transaction.processor.dto.StatusDto;
import cz.muni.pa165.banking.transaction.processor.dto.ThresholdDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
public class ProcessController implements ProcessApi {
    
    private final ProcessFacade processFacade;

    public ProcessController(ProcessFacade processFacade) {
        this.processFacade = processFacade;
    }

    @Override
    @PreAuthorize("hasAnyAuthority('SCOPE_test_2', 'SCOPE_test_3')")
    public ResponseEntity<ThresholdDto> getDefaultThreshold() {
        return ResponseEntity.ok(processFacade.getDefaultThreshold());
    }

    @Override
    @PreAuthorize("hasAnyAuthority('SCOPE_test_2', 'SCOPE_test_3')")
    public ResponseEntity<ProcessStatusDto> getProcessStatus(UUID uuid) {
        return ResponseEntity.ok(processFacade.getProcessStatus(uuid));
    }

    @Override
    @PreAuthorize("hasAnyAuthority('SCOPE_test_2', 'SCOPE_test_3')")
    public ResponseEntity<ProcessStatusListDto> getProcessesOfState(StatusDto status) {
        return ResponseEntity.ok(processFacade.getProcessesOfState(status));
    }

    @Override
    @PreAuthorize("hasAnyAuthority('SCOPE_test_2', 'SCOPE_test_3')")
    public ResponseEntity<ProcessStatusListDto> getUnresolvedProcesses(LocalDate date) {
        return ResponseEntity.ok(processFacade.getUnresolvedProcesses(date));
    }

    @Override
    @PreAuthorize("hasAnyAuthority('SCOPE_test_2', 'SCOPE_test_3')")
    public ResponseEntity<List<UUID>> resolveProcesses(LocalDate date) {
        return ResponseEntity.ok(processFacade.resolveProcesses(date));
    }
}
