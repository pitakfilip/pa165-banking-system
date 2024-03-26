package cz.muni.pa165.banking.application.repository;

import cz.muni.pa165.banking.domain.balance.Balance;
import cz.muni.pa165.banking.domain.balance.repository.BalancesRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

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
    @Override
    public Optional<Balance> findById(Integer id) {
        //return allBalances.get(id);
        if( mockData.containsKey(id)){
            return Optional.of(mockData.get(id));
        };
        return Optional.empty();
    }

    @Transactional
    @Override
    public void addBalance(Integer id){
        mockData.put(id, new Balance());
    }
}
