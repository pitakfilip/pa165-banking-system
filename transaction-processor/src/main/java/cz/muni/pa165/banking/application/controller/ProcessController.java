package cz.muni.pa165.banking.application.controller;

import cz.muni.pa165.banking.transaction.processor.ProcessApi;
import cz.muni.pa165.banking.transaction.processor.dto.ProcessStatusDto;
import cz.muni.pa165.banking.transaction.processor.dto.ProcessStatusListDto;
import cz.muni.pa165.banking.transaction.processor.dto.StatusDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
public class ProcessController implements ProcessApi {
    
    @Override
    public ResponseEntity<LocalDate> getDefaultThreshold() {
        return null;
    }

    @Override
    public ResponseEntity<ProcessStatusDto> getProcessStatus(UUID uuid) {
        return null;
    }

    @Override
    public ResponseEntity<ProcessStatusListDto> getProcessesOfState(StatusDto status) {
        return null;
    }

    @Override
    public ResponseEntity<ProcessStatusListDto> getUnresolvedProcesses(LocalDate date) {
        return null;
    }

    @Override
    public ResponseEntity<List<UUID>> resolveProcesses(LocalDate date) {
        return null;
    }
}
