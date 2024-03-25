package cz.muni.pa165.banking.application.repository;

import cz.muni.pa165.banking.domain.allbalances.repository.AllBalancesRepository;
import cz.muni.pa165.banking.domain.balance.Balance;
import cz.muni.pa165.banking.domain.balance.repository.BalanceRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Martin Mojzis
 */
@Repository
public class AllBalancesRepositoryImpl implements AllBalancesRepository {

    private final Map<Integer, BalanceRepository> allBalances = new HashMap<>();
    @Transactional
    public BalanceRepository getById(Integer id) {
        return allBalances.get(id);
    }
}
