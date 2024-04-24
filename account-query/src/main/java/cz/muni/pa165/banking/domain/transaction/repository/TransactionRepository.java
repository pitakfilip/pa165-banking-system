package cz.muni.pa165.banking.domain.transaction.repository;

import cz.muni.pa165.banking.domain.balance.Balance;
import cz.muni.pa165.banking.domain.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * @author Martin Mojzis
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT u FROM Transaction u where u.balance = :balance")
    Collection<Transaction> findByBalance(Balance balance);
}
