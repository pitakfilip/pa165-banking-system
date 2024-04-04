package cz.muni.pa165.banking.domain.scheduled.payments.repository;

import cz.muni.pa165.banking.account.management.dto.ScheduledPayment;

import java.util.Map;

public interface ScheduledPaymentRepository {
    /**
     * Adds a new account to the repository.
     *
     * @param scheduledPayment the scheduled payment to add
     * @return true if the payment was scheduled successfully, false if a scheduled payment with the same ID already exists
     */
    boolean addScheduledPayment(ScheduledPayment scheduledPayment);

    /**
     * Retrieves an account by its ID.
     *
     * @param id the ID of the scheduled payment to retrieve
     * @return the scheduled payment with the specified ID, or null if no such scheduled payment exists
     */
    ScheduledPayment getById(String id);

    /**
     * Retrieves the map of all the scheduled payments.
     *
     * @return the map of all the scheduled payments
     */
    Map<String, ScheduledPayment> getAllPayments();
}
