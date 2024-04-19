package cz.muni.pa165.banking.domain.scheduled;

import cz.muni.pa165.banking.domain.scheduled.repository.ScheduledPaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

public class ScheduledPaymentProcessorTest {

    @Mock
    private ScheduledPaymentRepository scheduledPaymentRepository;

    @InjectMocks
    private ScheduledPaymentProcessor scheduledPaymentProcessor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testExecuteScheduledPayments() {
        // Arrange
        Map<Long, ScheduledPayment> scheduledPayments = new HashMap<>();
        ScheduledPayment scheduledPayment = new ScheduledPayment();
        scheduledPayment.setSenderAccountId(1L);
        scheduledPayment.setReceiverAccountId(2L);
        scheduledPayment.setAmount(100);
        scheduledPayments.put(1L, scheduledPayment);

        when(scheduledPaymentRepository.getAllScheduledPayments()).thenReturn(scheduledPayments);

        // Act
        scheduledPaymentProcessor.executeScheduledPayments();

        // Assert
        verify(scheduledPaymentRepository, times(1)).getAllScheduledPayments();
    }
}

