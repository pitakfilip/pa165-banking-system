package cz.muni.pa165.banking.application.service;

import cz.muni.pa165.banking.application.exception.NotFoundAccountException;
import cz.muni.pa165.banking.domain.balance.Balance;
import cz.muni.pa165.banking.domain.balance.repository.BalancesRepository;
import cz.muni.pa165.banking.domain.balance.service.BalanceService;
import cz.muni.pa165.banking.domain.report.StatisticalReport;
import cz.muni.pa165.banking.domain.transaction.Transaction;
import cz.muni.pa165.banking.domain.transaction.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Martin Mojzis
 */
@Service
public class BalanceServiceImpl implements BalanceService {
    private final BalancesRepository balanceRepository;

    @Autowired
    public BalanceServiceImpl(BalancesRepository balanceRepository){
        this.balanceRepository = balanceRepository;
    }

    @Override
    public void addNewBalance(String id) throws NotFoundAccountException {
        balanceRepository.addNewBalance(id);
    }

    @Override
    public BigDecimal getBalance(String id) throws NotFoundAccountException {
        this.findById(id);
        return balanceRepository.getBalance(id);
    }

    @Override
    public List<Transaction> getTransactions(String id, OffsetDateTime from, OffsetDateTime to, BigDecimal minAmount,
                                             BigDecimal maxAmount, TransactionType type)
            throws NotFoundAccountException {
        this.findById(id);
        if (minAmount == null && maxAmount == null && type == null)
            return balanceRepository.getTransactions(id, from, to, BigDecimal.valueOf(Integer.MIN_VALUE),
                    BigDecimal.valueOf(Integer.MAX_VALUE));
        if (minAmount == null && maxAmount == null)
            return balanceRepository.getTransactions(id, from, to, BigDecimal.valueOf(Integer.MIN_VALUE),
                    BigDecimal.valueOf(Integer.MAX_VALUE), type);
        if (maxAmount == null && type == null)
            return balanceRepository.getTransactions(id, from, to, minAmount, BigDecimal.valueOf(Integer.MAX_VALUE));
        if (minAmount == null && type == null)
            return balanceRepository.getTransactions(id, from, to, BigDecimal.valueOf(Integer.MIN_VALUE), maxAmount);
        if (maxAmount == null)
            return balanceRepository.getTransactions(id, from, to, minAmount, BigDecimal.valueOf(Integer.MAX_VALUE), type);
        if (minAmount == null) return balanceRepository.getTransactions(id, from, to, BigDecimal.ZERO, maxAmount, type);
        return balanceRepository.getTransactions(id, from, to, minAmount, maxAmount, type);
    }

    @Override
    public void addToBalance(String id, BigDecimal amount, String processID, TransactionType type)
            throws NotFoundAccountException {
        this.findById(id);
        balanceRepository.addToBalance(id, amount, processID, type);
    }

    @Override
    public StatisticalReport getReport(String id,  OffsetDateTime beginning,  OffsetDateTime end) {
        this.findById(id);
        return balanceRepository.getReport(id, beginning, end);
    }

    @Override
    public List<Transaction> getAllTransactions(OffsetDateTime from, OffsetDateTime to, BigDecimal minAmount,
                                                BigDecimal maxAmount, TransactionType transactionType) {
        List<Transaction> result = new LinkedList<>();
        for (String id: balanceRepository.getAllIds()) {
            result.addAll(getTransactions(id, from,  to, minAmount, maxAmount, transactionType));
        }
        return result;
    }

    @Transactional(readOnly = true)
    public Balance findById(String id) throws NotFoundAccountException {
        return balanceRepository.findById(id)
                .orElseThrow(() -> new NotFoundAccountException("Balance  of person with id: " + id + " was not found."));
    }
}
