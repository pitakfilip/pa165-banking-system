package cz.muni.pa165.banking.application.facade;
import cz.muni.pa165.banking.account.query.dto.Balance;
import cz.muni.pa165.banking.application.mapper.BalanceMapper;
import cz.muni.pa165.banking.application.service.BalanceServiceImpl;
import cz.muni.pa165.banking.domain.balance.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author Martin Mojzis
 */
@Service
public class BalanceFacade {
    private final BalanceService balanceService;
    private final BalanceMapper balanceMapper;
    @Autowired
    public BalanceFacade(BalanceService balanceService, BalanceMapper balanceMapper) {
        this.balanceService = balanceService;
        this.balanceMapper = balanceMapper;
    }

    public void createNewBalance(String id) {
        if(! balanceService.addNewBalance(id)){
            throw new RuntimeException("Balance for person with id:" + id + " already exists.");
        };
    }

    public void addToBalance(String id, BigDecimal value){

    }

    public BigDecimal getBalance(String id){
        return balanceService.getBalance(id);
    }
}
