package cz.muni.pa165.banking.domain.scheduled.repository;

import cz.muni.pa165.banking.domain.scheduled.ScheduledPayment;
import cz.muni.pa165.banking.domain.scheduled.recurrence.Recurrence;
import cz.muni.pa165.banking.domain.scheduled.recurrence.RecurrenceQuerySpecificationBuilder;
import cz.muni.pa165.banking.domain.scheduled.recurrence.RecurrenceType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class ScheduledPaymentRepositoryTest {

    @Autowired
    private ScheduledPaymentRepository repository;
    
    @BeforeAll
    public static void initDb(@Autowired ScheduledPaymentRepository repository) {
        repository.save(ScheduledPaymentBuilder.create(1L, RecurrenceType.MONTHLY, 1));
        repository.save(ScheduledPaymentBuilder.create(1L, RecurrenceType.MONTHLY, 2));
        repository.save(ScheduledPaymentBuilder.create(2L, RecurrenceType.MONTHLY, 3));
        repository.save(ScheduledPaymentBuilder.create(2L, RecurrenceType.MONTHLY, 3));
        repository.save(ScheduledPaymentBuilder.create(2L, RecurrenceType.MONTHLY, 3));
        repository.save(ScheduledPaymentBuilder.create(2L, RecurrenceType.MONTHLY, 31));
        repository.save(ScheduledPaymentBuilder.create(2L, RecurrenceType.MONTHLY, 30));
        repository.save(ScheduledPaymentBuilder.create(2L, RecurrenceType.MONTHLY, 29));
        repository.save(ScheduledPaymentBuilder.create(2L, RecurrenceType.MONTHLY, 28));
        repository.save(ScheduledPaymentBuilder.create(2L, RecurrenceType.MONTHLY, 27));

        repository.save(ScheduledPaymentBuilder.create(2L, RecurrenceType.WEEKLY, 1));
        repository.save(ScheduledPaymentBuilder.create(2L, RecurrenceType.WEEKLY, 1));
        repository.save(ScheduledPaymentBuilder.create(2L, RecurrenceType.WEEKLY, 3));
        repository.save(ScheduledPaymentBuilder.create(2L, RecurrenceType.WEEKLY, 3));
        repository.save(ScheduledPaymentBuilder.create(2L, RecurrenceType.WEEKLY, 3));
    }

    @Test
    void findAll_ReturnsAllPayments(){
        // When
        List<ScheduledPayment> foundPayments = repository.findAll();

        // Then
        assertEquals(15, foundPayments.size());
    }

    @Test
    void findBySourceAccountId_ValidId_ReturnsPayment() {
        // Given
        long validAccountId = 1L;

        // When
        List<ScheduledPayment> foundPayments = repository.findBySourceAccountId(validAccountId);

        // Then
        assertNotNull(foundPayments);
        assertEquals(2, foundPayments.size());
        assertEquals(1L, foundPayments.get(0).getId());
        assertEquals(2L, foundPayments.get(1).getId());
    }

    @Test
    void findBySourceAccountId_InvalidId_ReturnsEmptyList() {
        // Given
        long invalidAccountId = 999L;

        // When
        List<ScheduledPayment> foundPayments = repository.findBySourceAccountId(invalidAccountId);

        // Then
        assertNotNull(foundPayments);
        assertTrue(foundPayments.isEmpty());
    }
    
    @Test
    void fetchMonthlyPayments() {
        List<ScheduledPayment> payments = repository.findAll(RecurrenceQuerySpecificationBuilder.monthly());
        
        assertEquals(10, payments.size());
    }
    
    @Test
    void fetchWeeklyPayments() {
        List<ScheduledPayment> payments = repository.findAll(RecurrenceQuerySpecificationBuilder.weekly());
        
        assertEquals(5, payments.size());
    }

    /**
     * Testing the capability of fetching data with the created specification for a trivial date.
     * Trivial date in our case means a date, that causes no edge-cases such as
     * 'The monthly payment day is set to 31 but the current month has only 30 days'.
     * <br>
     * The result list of payments should include three monthly payments and three weekly payments.
     */
    @Test
    void fetchPaymentsForTrivialDate() {
        LocalDate trivialDate = LocalDate.of(2024, 4, 3);
        List<ScheduledPayment> payments = repository.findAll(RecurrenceQuerySpecificationBuilder.forDay(trivialDate));
        
        assertEquals(6, payments.size());
        assertEquals(3, payments.stream().filter(p -> p.getRecurrence().getType() == RecurrenceType.MONTHLY).toList().size());
        assertEquals(3, payments.stream().filter(p -> p.getRecurrence().getType() == RecurrenceType.WEEKLY).toList().size());
    }

    /**
     * Testing the capability of fetching data with the created specification for a non-trivial date.
     * In this case a non-trivial date means e.g. 30th of April, where April has only 30days. The leading issue
     * is with monthly payments set to the 31st day of the month, which is unreachable for April.
     * <br>
     * The result list should contain all scheduled monthly payments, where the payment day is later/equal to the last day of the month.
     */
    @Test
    void fetchPaymentsForLastAprilDay() {
        LocalDate nontrivialDate = LocalDate.of(2024, 4, 30); // Last day of April
        List<ScheduledPayment> payments = repository.findAll(RecurrenceQuerySpecificationBuilder.forDay(nontrivialDate));
        
        assertEquals(2, payments.size());
    }
    
    @Test
    void fetchPaymentsForLastFebruaryDay_LeapYear() {
        LocalDate nontrivialDate = LocalDate.of(2024, 2, 29); // Last day of February 2024 (leap year)
        List<ScheduledPayment> payments = repository.findAll(RecurrenceQuerySpecificationBuilder.forDay(nontrivialDate));
        
        assertEquals(3, payments.size());
    }
    
    @Test
    void fetchPaymentsForLastFebruaryDay_NonLeapYear() {
        LocalDate nontrivialDate = LocalDate.of(2023, 2, 28); // Last day of February 2023 (not a leap year)
        List<ScheduledPayment> payments = repository.findAll(RecurrenceQuerySpecificationBuilder.forDay(nontrivialDate));
        
        assertEquals(4, payments.size());
    }
    
    @Test
    void fetchMonthlyPaymentsNearEndOfMonth() {
        LocalDate trivialDate = LocalDate.of(2024, 2, 28);
        List<ScheduledPayment> payments = repository.findAll(RecurrenceQuerySpecificationBuilder.forDay(trivialDate));
        
        assertEquals(4, payments.size());
        assertEquals(1, payments.stream().filter(p -> p.getRecurrence().getType() == RecurrenceType.MONTHLY).toList().size());
        assertEquals(3, payments.stream().filter(p -> p.getRecurrence().getType() == RecurrenceType.WEEKLY).toList().size());
    }
    
    
    private static final class ScheduledPaymentBuilder {
        
        private static long id = 1L;
        
        static ScheduledPayment create(Long senderAccountId, RecurrenceType type, int paymentDay) {
            ScheduledPayment result = new ScheduledPayment();
            result.setId(id++);
            result.setSourceAccountId(senderAccountId);
            Recurrence recurrence = new Recurrence();
            recurrence.setType(type);
            recurrence.setPaymentDay(paymentDay);
            result.setRecurrence(recurrence);
            
            return result;
        }
    }
}