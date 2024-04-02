package cz.muni.pa165.banking.application.repository;

import cz.muni.pa165.banking.domain.balance.Balance;
import cz.muni.pa165.banking.domain.balance.repository.BalancesRepository;
import cz.muni.pa165.banking.domain.report.StatisticalReport;
import cz.muni.pa165.banking.domain.transaction.Transaction;
import cz.muni.pa165.banking.domain.transaction.TransactionType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
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

    @Override
    public boolean addNewBalance(String id) {
        if(mockData.containsKey(id)){
            return false;
        }
        mockData.put(id, new Balance(id));
        return true;
    }

    @Override
    public BigDecimal getBalance(String id) {
        return mockData.get(id).getAmount();
    }

    @Override
    public List<String> getAllIds() {
        return mockData.keySet().stream().toList();
    }

    @Override
    public List<Transaction> getTransactions(String id, OffsetDateTime from, OffsetDateTime to, BigDecimal minAmount, BigDecimal maxAmount) {
        return mockData.get(id).getData(from, to, minAmount, maxAmount);
    }

    @Override
    public List<Transaction> getTransactions(String id, OffsetDateTime from, OffsetDateTime to, BigDecimal minAmount, BigDecimal maxAmount, TransactionType type) {
        return mockData.get(id).getData(from, to, minAmount, maxAmount, type);
    }

    @Override
    public void addToBalance(String id, BigDecimal amount, String processID, TransactionType type) {
        mockData.get(id).AddTransaction(amount, type, processID);
    }

    @Override
    public StatisticalReport getReport(String id,  OffsetDateTime beginning,  OffsetDateTime end) {
        return mockData.get(id).getReport(beginning, end);
    }

    @Transactional
    @Override
    public void addBalance(String id) {
        mockData.put(id, new Balance(id));
    }

    //monitor all customers bank transactions - between specified dates
    // TODO with filers like transaction type, amount - call different getData with specified max, min value, type
    @Transactional
    public List<Transaction> getAllTransactions(OffsetDateTime from, OffsetDateTime to) {
        List<Transaction> toReturn = new ArrayList<>();
        mockData.forEach((i, balance) -> toReturn.addAll(balance.getData(from, to)));
        return toReturn;
    }

}
