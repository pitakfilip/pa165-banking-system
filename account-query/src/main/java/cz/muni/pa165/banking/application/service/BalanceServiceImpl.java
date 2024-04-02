package cz.muni.pa165.banking.application.service;

import cz.muni.pa165.banking.domain.balance.Balance;
import cz.muni.pa165.banking.domain.balance.repository.BalancesRepository;
import cz.muni.pa165.banking.domain.balance.service.BalanceService;
import cz.muni.pa165.banking.domain.transaction.Transaction;
import cz.muni.pa165.banking.domain.transaction.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
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
        this.findById(id);
        balanceRepository.addNewBalance(id);
    }

    @Override
    public BigDecimal getBalance(String id) throws NotFoundAccountException {
        this.findById(id);
        return balanceRepository.getBalance(id);
    }

    @Override
    public List<Transaction> getTransactions(String id, Date from, Date to) throws NotFoundAccountException {
        this.findById(id);
        return balanceRepository.getTransactions(id, from, to);
    }

    @Override
    public void addToBalance(String id, BigDecimal amount, String processID, TransactionType type) throws NotFoundAccountException {
        this.findById(id);
        balanceRepository.addToBalance(id, amount, processID, type);
    }

    @Transactional(readOnly = true)
    public Balance findById(String id) throws NotFoundAccountException {
        return balanceRepository.findById(id)
                .orElseThrow(() -> new NotFoundAccountException("Balance  of person with id: " + id + " was not found."));
    }
}
