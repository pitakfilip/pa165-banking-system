package cz.muni.pa165.banking.domain.scheduled.payments;

import cz.muni.pa165.banking.account.management.dto.ScheduledPayment;
import cz.muni.pa165.banking.application.repository.ScheduledPaymentRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ScheduledPaymentProcessor {
    private final ScheduledPaymentRepositoryImpl scheduledPaymentRepository;

    @Autowired
    public ScheduledPaymentProcessor(ScheduledPaymentRepositoryImpl scheduledPaymentRepository) {
        this.scheduledPaymentRepository = scheduledPaymentRepository;
    }

    @Scheduled(cron = "0 0 0 * * MON") // Execute every Monday at midnight
    public void executeScheduledPayments() {
        Map<String, ScheduledPayment> scheduledPayments = scheduledPaymentRepository.getAllPayments();
        for (ScheduledPayment scheduledPayment : scheduledPayments.values()) {
            String senderAccountId = scheduledPayment.getSenderAccountId();
            String receiverAccountId = scheduledPayment.getReceiverAccountId();
            Integer amount = scheduledPayment.getAmount();

            // call transaction processor

        }
    }

}