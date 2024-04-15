package cz.muni.pa165.banking.domain.balance.repository;

import cz.muni.pa165.banking.domain.balance.Balance;
import cz.muni.pa165.banking.domain.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Martin Mojzis
 */
@Repository
public interface BalancesRepository extends JpaRepository<Balance, String> {

    @Query("SELECT u FROM Balance u where u.accountId = :id")
    Optional<Balance> findById(String id);

    default void addBalance(String id) {
        this.save(new Balance(id));
    }

    @Query("SELECT u.accountId FROM Balance u")
    List<String> getAllIds();
}
