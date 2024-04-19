package cz.muni.pa165.banking.domain.account.repository;

import cz.muni.pa165.banking.domain.account.Account;
import cz.muni.pa165.banking.domain.scheduled.ScheduledPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    
    Optional<Account> findByAccountNumber(String accountNumber);

    @Query("SELECT p FROM ScheduledPayment p where p.senderAccountId = :accountId")
    List<ScheduledPayment> findScheduledPaymentsById(Long accountId);
}
