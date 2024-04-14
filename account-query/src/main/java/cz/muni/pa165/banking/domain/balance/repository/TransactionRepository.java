package cz.muni.pa165.banking.domain.balance.repository;

import cz.muni.pa165.banking.domain.balance.Balance;
import cz.muni.pa165.banking.domain.transaction.Transaction;
import cz.muni.pa165.banking.domain.transaction.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * @author Martin Mojzis
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    default void addTransaction(BigDecimal amount, TransactionType type, UUID processId, Balance balance) {
        Transaction tr = new Transaction(type, amount,
                OffsetDateTime.now(), processId, balance);
        this.save(tr);
    }
}
