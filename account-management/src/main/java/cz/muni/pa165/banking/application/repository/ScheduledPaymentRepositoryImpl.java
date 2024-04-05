package cz.muni.pa165.banking.application.repository;

import cz.muni.pa165.banking.domain.scheduled.ScheduledPayment;
import cz.muni.pa165.banking.domain.scheduled.repository.ScheduledPaymentRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ScheduledPaymentRepositoryImpl implements ScheduledPaymentRepository {
    
    private Long sequencer = 1L;
    private Map<Long, ScheduledPayment> scheduledPayments = new HashMap<>();
    
    @Override
    public ScheduledPayment addScheduledPayment(ScheduledPayment scheduledPayment) {
        scheduledPayment.setId(sequencer);
        scheduledPayments.put(sequencer++, scheduledPayment);
        return scheduledPayment;
    }

    @Override
    public ScheduledPayment getById(Long id) {
        return scheduledPayments.get(id);
    }

    @Override
    public Map<Long, ScheduledPayment> getAllPayments() {
        return scheduledPayments;
    }

}
