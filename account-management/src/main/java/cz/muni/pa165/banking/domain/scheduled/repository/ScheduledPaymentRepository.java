package cz.muni.pa165.banking.domain.scheduled.repository;

import cz.muni.pa165.banking.domain.scheduled.ScheduledPayment;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduledPaymentRepository extends JpaRepository<ScheduledPayment, Long> {

    List<ScheduledPayment> findAll(Specification<ScheduledPayment> spec);

    List<ScheduledPayment> findBySourceAccountId(Long accountId);
    
}
