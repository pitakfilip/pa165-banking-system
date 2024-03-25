package cz.muni.pa165.banking.application.service;

import cz.muni.pa165.banking.domain.allbalances.repository.AllBalancesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Martin Mojzis
 */
@Service
public class BalanceService {
    private AllBalancesRepository balanceRepository;

    @Autowired
    public BalanceService(AllBalancesRepository balanceRepository){
        this.balanceRepository = balanceRepository;
    }
}
