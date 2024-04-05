package cz.muni.pa165.banking.domain.scheduled;

import cz.muni.pa165.banking.application.repository.ScheduledPaymentRepositoryImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ScheduledPaymentProcessor {
    
    private final ScheduledPaymentRepositoryImpl scheduledPaymentRepository;

    public ScheduledPaymentProcessor(ScheduledPaymentRepositoryImpl scheduledPaymentRepository) {
        this.scheduledPaymentRepository = scheduledPaymentRepository;
    }

    @Scheduled(cron = "${scheduled.cron.expression}") 
    public void executeScheduledPayments() {
        Map<Long, ScheduledPayment> scheduledPayments = scheduledPaymentRepository.getAllPayments();
        for (ScheduledPayment scheduledPayment : scheduledPayments.values()) {
            Long senderAccountId = scheduledPayment.getSenderAccountId();
            Long receiverAccountId = scheduledPayment.getReceiverAccountId();
            Integer amount = scheduledPayment.getAmount();

            // call transaction processor

        }
    }

}