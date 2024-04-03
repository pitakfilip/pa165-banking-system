package cz.muni.pa165.banking.application.repository;

import cz.muni.pa165.banking.domain.balance.Balance;
import cz.muni.pa165.banking.domain.balance.repository.BalancesRepository;
import cz.muni.pa165.banking.domain.report.StatisticalReport;
import cz.muni.pa165.banking.domain.transaction.Transaction;
import cz.muni.pa165.banking.domain.transaction.TransactionType;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.*;

/**
 * @author Martin Mojzis
 */
@Repository
public class BalancesRepositoryImpl implements BalancesRepository {
    //private final Map<Integer, Balance> allBalances = new HashMap<>();
    private Map<String, Balance> mockData = new HashMap<>();

    public BalancesRepositoryImpl() {
        mockData.put("id1", new Balance("id1"));
        mockData.put("id2", new Balance("id2"));
    }

    //@Transactional
    @Override
    public Optional<Balance> findById(String id) {
        if (mockData.containsKey(id)) {
            return Optional.of(mockData.get(id));
        }
        return Optional.empty();
    }

    @Override
    public List<String> getAllIds() {
        return mockData.keySet().stream().toList();
    }


    //@Transactional
    @Override
    public void addBalance(String id) {
        mockData.put(id, new Balance(id));
    }
}
