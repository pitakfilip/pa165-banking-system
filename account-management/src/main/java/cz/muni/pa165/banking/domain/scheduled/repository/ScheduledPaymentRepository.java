package cz.muni.pa165.banking.domain.scheduled.repository;

import cz.muni.pa165.banking.domain.scheduled.ScheduledPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
public interface ScheduledPaymentRepository extends JpaRepository<ScheduledPayment, Long> {
    /**
     * Adds a new account to the repository.
     *
     * @param scheduledPayment the scheduled payment to add
     */
    default ScheduledPayment addScheduledPayment(ScheduledPayment scheduledPayment){
        return saveAndFlush(scheduledPayment);
    }

    /**
     * Retrieves an account by its ID.
     *
     * @param id the ID of the scheduled payment to retrieve
     * @return the scheduled payment with the specified ID, or null if no such scheduled payment exists
     */
    @Query("SELECT s FROM ScheduledPayment s where s.id = :id")
    Optional<ScheduledPayment> findById(Long id);

    /**
     * Retrieves the map of all the scheduled payments.
     *
     * @return the map of all the scheduled payments
     */
    @Query("SELECT s FROM ScheduledPayment s")
    Map<Long, ScheduledPayment> getAllScheduledPayments();
}
