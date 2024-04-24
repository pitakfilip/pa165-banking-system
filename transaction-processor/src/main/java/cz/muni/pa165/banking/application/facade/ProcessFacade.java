package cz.muni.pa165.banking.application.facade;

import cz.muni.pa165.banking.application.service.ProcessService;
import cz.muni.pa165.banking.domain.process.Process;
import cz.muni.pa165.banking.domain.process.status.Status;
import cz.muni.pa165.banking.transaction.processor.dto.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

@Component
public class ProcessFacade {

    @Value("${process.resolution.threshold}")
    private int thresholdDays;

    private final ProcessService service;

    public ProcessFacade(ProcessService service) {
        this.service = service;
    }

    public ThresholdDto getDefaultThreshold() {
        return new ThresholdDto()
                .message("The default threshold is set to be " + thresholdDays + " days ago.")
                .currentThreshold(defaultThreshold());
    }

    public ProcessStatusDto getProcessStatus(UUID uuid) {
        Process process = service.findByUuid(uuid);
        return map(process);
    }

    public ProcessStatusListDto getProcessesOfState(StatusDto statusDto) {
        OffsetDateTime now = OffsetDateTime.now();
        Status status = Status.valueOf(statusDto.name());
        List<Process> processes = service.processesWithStatus(status);

        return new ProcessStatusListDto()
                .when(now)
                .processes(
                        processes.stream()
                                .map(this::map)
                                .toList()
                );
    }

    public ProcessStatusListDto getUnresolvedProcesses(LocalDate threshold) {
        OffsetDateTime now = OffsetDateTime.now();
        
        if (threshold == null) {
            threshold = defaultThreshold();
        }
        List<Process> processes = service.unresolvedProcessesToDate(threshold);

        return new ProcessStatusListDto()
                .when(now)
                .processes(
                        processes.stream()
                                .map(this::map)
                                .toList()
                );
    }

    public List<UUID> resolveProcesses(LocalDate threshold) {
        if (threshold == null) {
            threshold = defaultThreshold();
        }
        return service.resolveProcesses(threshold);
    }

    private LocalDate defaultThreshold() {
        return LocalDate.now().minusDays(thresholdDays);
    }
    
    
    private ProcessStatusDto map(Process process) {
        return new ProcessStatusDto()
                .identifier(process.getUuid())
                .status(new StatusDetailDto()
                        .created(process.getWhen().atOffset(ZoneOffset.UTC))
                        .status(StatusDto.valueOf(process.getStatus().name()))
                        .information(process.getInformation()));
    }

}
