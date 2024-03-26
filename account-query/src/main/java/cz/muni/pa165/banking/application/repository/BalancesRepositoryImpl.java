package cz.muni.pa165.banking.application.repository;

import cz.muni.pa165.banking.domain.balance.Balance;
import cz.muni.pa165.banking.domain.balance.repository.BalancesRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @author Martin Mojzis
 */
@Repository
public class BalancesRepositoryImpl implements BalancesRepository {
    //private final Map<Integer, Balance> allBalances = new HashMap<>();
    private final Map<Integer, Balance> mockData;
    public BalancesRepositoryImpl() {
        mockData = Map.of(
                1, new Balance(),
                2, new Balance()
        );
    }
    @Transactional
    public Balance getById(Integer id) {
        //return allBalances.get(id);
        return mockData.get(id);
    }
}
