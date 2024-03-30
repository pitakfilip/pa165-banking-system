package cz.muni.pa165.banking.domain.balance.repository;

import cz.muni.pa165.banking.domain.balance.Balance;

import java.util.Optional;

/**
 * @author Martin Mojzis
 */
public interface BalancesRepository {

    Optional<Balance> findById(String id);
    void addBalance(String id);
}
