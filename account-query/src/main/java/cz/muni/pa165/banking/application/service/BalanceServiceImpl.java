package cz.muni.pa165.banking.application.service;

import cz.muni.pa165.banking.application.exception.NotFoundAccountException;
import cz.muni.pa165.banking.domain.balance.Balance;
import cz.muni.pa165.banking.domain.balance.repository.BalancesRepository;
import cz.muni.pa165.banking.domain.balance.repository.TransactionRepository;
import cz.muni.pa165.banking.domain.balance.service.BalanceService;
import cz.muni.pa165.banking.domain.report.StatisticalReport;
import cz.muni.pa165.banking.domain.transaction.Transaction;
import cz.muni.pa165.banking.domain.transaction.TransactionType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Martin Mojzis
 */
@Service
public class BalanceServiceImpl implements BalanceService {

    private final BalancesRepository balanceRepository;
    private final TransactionRepository transactionRepository;

    public BalanceServiceImpl(BalancesRepository balanceRepository, TransactionRepository transactionRepository) {
        this.balanceRepository = balanceRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public Balance findById(String id) throws NotFoundAccountException {
        return balanceRepository.findById(id)
                .orElseThrow(() -> new NotFoundAccountException("Balance  of person with id: " + id + " was not found."));
    }

    @Override
    @Transactional
    public void addNewBalance(String id) {
        balanceRepository.addBalance(id);
    }

    @Override
    @Transactional
    public BigDecimal getBalance(String id) throws NotFoundAccountException {
        Balance balance = findById(id);
        return balance.getAmount();
    }

    @Override
    @Transactional
    public List<Transaction> getTransactions(String id, OffsetDateTime from, OffsetDateTime to, BigDecimal minAmount,
                                             BigDecimal maxAmount, TransactionType type)
            throws NotFoundAccountException {
        Balance balance = findById(id);
        if (minAmount == null && maxAmount == null && type == null)
            return balance.getData(from, to);
        if (minAmount == null && maxAmount == null)
            return balance.getData(from, to, type);
        BigDecimal amountMax = Objects.requireNonNullElse(maxAmount, BigDecimal.valueOf(Integer.MAX_VALUE));
        BigDecimal amountMin = Objects.requireNonNullElse(minAmount, BigDecimal.valueOf(Integer.MIN_VALUE));
        if (type == null)
            return balance.getData(from, to, amountMin, amountMax);
        return balance.getData(from, to, amountMin, amountMax, type);
    }

    @Override
    @Transactional
    public void addToBalance(String id, BigDecimal amount, UUID processID, TransactionType type)
            throws NotFoundAccountException {
        Balance balance = findById(id);
        Transaction t = balance.addTransaction(amount, type, processID);
        balanceRepository.save(balance);
        transactionRepository.save(t);
    }

    @Override
    @Transactional
    public StatisticalReport getReport(String id, OffsetDateTime beginning, OffsetDateTime end) {
        Balance balance = findById(id);
        return balance.getReport(beginning, end);
    }

    @Override
    @Transactional
    public List<Transaction> getAllTransactions(OffsetDateTime from, OffsetDateTime to, BigDecimal minAmount,
                                                BigDecimal maxAmount, TransactionType transactionType) {
        List<Transaction> result = new LinkedList<>();
        for (String id : balanceRepository.getAllIds()) {
            result.addAll(getTransactions(id, from, to, minAmount, maxAmount, transactionType));
        }
        return result;
    }

    @Override
    public void deleteBalance(String id) throws NotFoundAccountException {
        Balance balance = findById(id);
        transactionRepository.findByBalance(balance).forEach(a -> transactionRepository.delete(a));
        balanceRepository.delete(balance);
    }
}
