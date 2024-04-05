package cz.muni.pa165.banking.domain.scheduled;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScheduledPaymentTest {
    @Test
    public void testGettersAndSetters() {
        // Arrange
        ScheduledPayment scheduledPayment = new ScheduledPayment();
        Long id = 1L;
        Long senderAccountId = 2L;
        Long receiverAccountId = 3L;
        Integer amount = 1000;

        // Act
        scheduledPayment.setId(id);
        scheduledPayment.setSenderAccountId(senderAccountId);
        scheduledPayment.setReceiverAccountId(receiverAccountId);
        scheduledPayment.setAmount(amount);

        // Assert
        assertEquals(id, scheduledPayment.getId());
        assertEquals(senderAccountId, scheduledPayment.getSenderAccountId());
        assertEquals(receiverAccountId, scheduledPayment.getReceiverAccountId());
        assertEquals(amount, scheduledPayment.getAmount());
    }

    @Test
    public void testDefaultConstructor() {
        // Act
        ScheduledPayment scheduledPayment = new ScheduledPayment();

        // Assert
        assertNull(scheduledPayment.getId());
        assertNull(scheduledPayment.getSenderAccountId());
        assertNull(scheduledPayment.getReceiverAccountId());
        assertNull(scheduledPayment.getAmount());
    }

}