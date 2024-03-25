package cz.muni.pa165.banking.domain.allbalances.repository;

import cz.muni.pa165.banking.domain.balance.Balance;
import cz.muni.pa165.banking.domain.balance.repository.BalanceRepository;

/**
 * @author Martin Mojzis
 */
public interface AllBalancesRepository {
    public BalanceRepository getById(Integer Id);
}
