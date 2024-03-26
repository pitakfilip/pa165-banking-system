package cz.muni.pa165.banking.application.service;

import cz.muni.pa165.banking.domain.balance.repository.BalancesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Martin Mojzis
 */
@Service
public class BalanceServiceImpl {
    private BalancesRepository balanceRepository;

    @Autowired
    public BalanceServiceImpl(BalancesRepository balanceRepository){
        this.balanceRepository = balanceRepository;
    }
}
