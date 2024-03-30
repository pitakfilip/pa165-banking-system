package cz.muni.pa165.banking.application.repository;

import cz.muni.pa165.banking.domain.balance.Balance;
import cz.muni.pa165.banking.domain.balance.repository.BalancesRepository;
import cz.muni.pa165.banking.domain.transaction.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author Martin Mojzis
 */
@Repository
public class BalancesRepositoryImpl implements BalancesRepository {
    //private final Map<Integer, Balance> allBalances = new HashMap<>();
    private final Map<String, Balance> mockData;

    public BalancesRepositoryImpl() {
        mockData = Map.of(
                "id1", new Balance("id1"),
                "id2", new Balance("id2")
        );
    }

    @Transactional
    @Override
    public Optional<Balance> findById(String id) {
        //return allBalances.get(id);
        if (mockData.containsKey(id)) {
            return Optional.of(mockData.get(id));
        }
        return Optional.empty();
    }

    @Transactional
    @Override
    public void addBalance(String id) {
        mockData.put(id, new Balance(id));
    }

    //monitor all customers bank transactions - between specified dates
    // TODO with filers like transaction type, amount - call different getData
    @Transactional
    public List<Transaction> getAllTransactions(Date from, Date to) {
        List<Transaction> toReturn = new ArrayList<>();
        mockData.forEach((i, balance) -> toReturn.addAll(balance.getData(from, to)));
        return toReturn;
    }

}
