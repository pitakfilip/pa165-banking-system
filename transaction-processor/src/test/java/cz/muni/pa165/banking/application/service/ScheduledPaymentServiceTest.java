package cz.muni.pa165.banking.application.service;

import cz.muni.pa165.banking.account.management.AccountApi;
import cz.muni.pa165.banking.account.management.dto.ScheduledPaymentDto;
import cz.muni.pa165.banking.account.management.dto.ScheduledPaymentType;
import cz.muni.pa165.banking.account.management.dto.ScheduledPaymentsDto;
import cz.muni.pa165.banking.domain.messaging.MessageProducer;
import cz.muni.pa165.banking.domain.process.repository.ProcessRepository;
import cz.muni.pa165.banking.domain.process.repository.ProcessTransactionRepository;
import cz.muni.pa165.banking.exception.ServerError;
import org.awaitility.reflect.WhiteboxImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScheduledPaymentServiceTest {

    @Mock
    private AccountApi accountApi;

    @Mock
    private ProcessTransactionRepository processTransactionRepository;

    @Mock
    private ProcessRepository processRepository;

    @Mock
    private MessageProducer messageProducer;

    @InjectMocks
    private ScheduledPaymentService scheduledPaymentService;

    @Test
    void executeScheduledPayments_shouldCreateProcessesForScheduledPayments() {
        LocalDate date = LocalDate.now();
        ScheduledPaymentsDto validScheduledPaymentsDto = new ScheduledPaymentsDto();
        
        validScheduledPaymentsDto.addScheduledPaymentsItem(
                new ScheduledPaymentDto(BigDecimal.ONE, "EUR", ScheduledPaymentType.MONTHLY, 1)
        );
        validScheduledPaymentsDto.addScheduledPaymentsItem(
                new ScheduledPaymentDto(BigDecimal.ONE, "EUR", ScheduledPaymentType.MONTHLY, 1)
        );
        validScheduledPaymentsDto.addScheduledPaymentsItem(
                new ScheduledPaymentDto(BigDecimal.ONE, "EUR", ScheduledPaymentType.MONTHLY, 1)
        );

        when(accountApi.getScheduledPaymentsOf(date)).thenReturn(ResponseEntity.ok(validScheduledPaymentsDto));

        scheduledPaymentService.executeScheduledPayments(date);

        verify(processRepository, times(3)).save(any());
        verify(processTransactionRepository, times(3)).save(any());
        verify(messageProducer, times(3)).send(any());
    }

    @Test
    void executeScheduledPayments_shouldThrowServerErrorForInvalidResponse() {
        LocalDate date = LocalDate.now();

        when(accountApi.getScheduledPaymentsOf(date)).thenReturn(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());

        ServerError err = assertThrows(
                ServerError.class,
                () -> scheduledPaymentService.executeScheduledPayments(date)
        );
        String cause = WhiteboxImpl.getInternalState(err, "cause");
        assertEquals("Call to Account Management service unsuccessful.", cause);

        verify(processRepository, never()).save(any());
    }

}