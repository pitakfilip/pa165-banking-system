package cz.muni.pa165.banking.application.service;

import cz.muni.pa165.banking.domain.balance.Balance;
import cz.muni.pa165.banking.domain.balance.repository.BalancesRepository;
import cz.muni.pa165.banking.domain.balance.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public boolean addNewBalance(String id) {
        try{
            this.findById(id);
        }
        catch (RuntimeException e){
            balanceRepository.addBalance(id);
            return true;
        }
        return false;
    }
    @Transactional(readOnly = true)
    public Balance findById(String id) {
        return balanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Balance  of person with id: " + id + " was not found."));
    }
}
