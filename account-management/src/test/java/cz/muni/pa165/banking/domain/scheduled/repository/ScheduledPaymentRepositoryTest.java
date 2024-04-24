package cz.muni.pa165.banking.domain.scheduled.repository;

import cz.muni.pa165.banking.domain.scheduled.ScheduledPayment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScheduledPaymentRepositoryTest {
    @Mock
    private ScheduledPaymentRepository scheduledPaymentRepository;

    @Test
    void findAll_ReturnsAllPayments(){
        // Given
        ScheduledPayment payment1 = new ScheduledPayment();
        payment1.setId(1L);
        ScheduledPayment payment2 = new ScheduledPayment();
        payment2.setId(2L);
        List<ScheduledPayment> payments = Arrays.asList(payment1, payment2);

        when(scheduledPaymentRepository.findAll()).thenReturn(payments);

        // When
        List<ScheduledPayment> foundPayments = scheduledPaymentRepository.findAll();

        // Then
        assertEquals(2, foundPayments.size());
        assertEquals(1L, foundPayments.get(0).getId());
        assertEquals(2L, foundPayments.get(1).getId());
    }

    @Test
    void findBySourceAccountId_ValidId_ReturnsPayment() {
        // Given
        long validAccountId = 1L;
        ScheduledPayment payment1 = new ScheduledPayment();
        payment1.setId(1L);
        payment1.setSourceAccountId(validAccountId);

        ScheduledPayment payment2 = new ScheduledPayment();
        payment2.setId(2L);
        payment2.setSourceAccountId(validAccountId);

        List<ScheduledPayment> payments = new ArrayList<>();
        payments.add(payment1);
        payments.add(payment2);

        when(scheduledPaymentRepository.findBySourceAccountId(validAccountId)).thenReturn(payments);

        // When
        List<ScheduledPayment> foundPayments = scheduledPaymentRepository.findBySourceAccountId(validAccountId);

        // Then
        assertNotNull(foundPayments);
        assertEquals(2, foundPayments.size());
        assertEquals(1L, foundPayments.get(0).getId());
        assertEquals(2L, foundPayments.get(1).getId());
    }

    @Test
    void findBySourceAccountId_InvalidId_ReturnsNull() {
        // Given
        long invalidAccountId = 999L;

        when(scheduledPaymentRepository.findBySourceAccountId(invalidAccountId)).thenReturn(null);

        // When
        List<ScheduledPayment> foundPayments = scheduledPaymentRepository.findBySourceAccountId(invalidAccountId);

        // Then
        assertNull(foundPayments);
    }
}