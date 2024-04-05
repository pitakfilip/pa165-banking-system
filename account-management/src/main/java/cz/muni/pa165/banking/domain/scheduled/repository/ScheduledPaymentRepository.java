package cz.muni.pa165.banking.domain.scheduled.repository;

import cz.muni.pa165.banking.domain.scheduled.ScheduledPayment;

import java.util.Map;

public interface ScheduledPaymentRepository {
    /**
     * Adds a new account to the repository.
     *
     * @param scheduledPayment the scheduled payment to add
     */
    ScheduledPayment addScheduledPayment(ScheduledPayment scheduledPayment);

    /**
     * Retrieves an account by its ID.
     *
     * @param id the ID of the scheduled payment to retrieve
     * @return the scheduled payment with the specified ID, or null if no such scheduled payment exists
     */
    ScheduledPayment getById(Long id);

    /**
     * Retrieves the map of all the scheduled payments.
     *
     * @return the map of all the scheduled payments
     */
    Map<Long, ScheduledPayment> getAllPayments();
}
