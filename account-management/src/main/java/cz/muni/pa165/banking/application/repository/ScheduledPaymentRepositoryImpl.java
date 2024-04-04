package cz.muni.pa165.banking.application.repository;

import cz.muni.pa165.banking.account.management.dto.ScheduledPayment;
import cz.muni.pa165.banking.domain.scheduled.payments.repository.ScheduledPaymentRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ScheduledPaymentRepositoryImpl implements ScheduledPaymentRepository {
    private Map<String, ScheduledPayment> scheduledPayments = new HashMap<>();
    @Override
    public boolean addScheduledPayment(ScheduledPayment scheduledPayment) {
        if (scheduledPayments.get(scheduledPayment.getId()) != null){
            return false;
        }
        scheduledPayments.put(scheduledPayment.getId(), scheduledPayment);
        return true;
    }

    @Override
    public ScheduledPayment getById(String id) {
        return scheduledPayments.get(id);
    }

    @Override
    public Map<String, ScheduledPayment> getAllPayments() {
        return scheduledPayments;
    }
}
