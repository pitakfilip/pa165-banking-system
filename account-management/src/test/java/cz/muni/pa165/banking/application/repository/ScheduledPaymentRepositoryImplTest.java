package cz.muni.pa165.banking.application.repository;

import cz.muni.pa165.banking.domain.scheduled.ScheduledPayment;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class ScheduledPaymentRepositoryImplTest {
    @InjectMocks
    private ScheduledPaymentRepositoryImpl scheduledPaymentRepository;

    @Test
    void addScheduledPayment_ValidScheduledPayment_ReturnsScheduledPayment() {
        // Arrange
        ScheduledPayment scheduledPayment = new ScheduledPayment();

        // Act
        ScheduledPayment result = scheduledPaymentRepository.addScheduledPayment(scheduledPayment);

        // Assert
        assertEquals(scheduledPayment, result);
    }

    @Test
    void getById_ExistingId_ReturnsScheduledPayment() {
        // Arrange
        ScheduledPayment scheduledPayment = new ScheduledPayment();
        scheduledPaymentRepository.addScheduledPayment(scheduledPayment);
        Long id = scheduledPayment.getId();

        // Act
        ScheduledPayment result = scheduledPaymentRepository.getById(id);

        // Assert
        assertEquals(scheduledPayment, result);
    }

    @Test
    void getById_NonExistingId_ReturnsNull() {
        // Arrange
        Long nonExistingId = 123L;

        // Act
        ScheduledPayment result = scheduledPaymentRepository.getById(nonExistingId);

        // Assert
        assertNull(result);
    }

    @Test
    void getAllPayments_ReturnsAllPayments() {
        // Arrange
        ScheduledPayment scheduledPayment1 = new ScheduledPayment();
        ScheduledPayment scheduledPayment2 = new ScheduledPayment();
        scheduledPaymentRepository.addScheduledPayment(scheduledPayment1);
        scheduledPaymentRepository.addScheduledPayment(scheduledPayment2);

        // Act
        Map<Long, ScheduledPayment> result = scheduledPaymentRepository.getAllPayments();

        // Assert
        assertEquals(2, result.size());
        assertEquals(scheduledPayment1, result.get(scheduledPayment1.getId()));
        assertEquals(scheduledPayment2, result.get(scheduledPayment2.getId()));
    }
}
