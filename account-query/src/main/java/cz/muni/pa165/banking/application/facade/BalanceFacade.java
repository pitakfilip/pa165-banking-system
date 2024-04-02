package cz.muni.pa165.banking.application.facade;
import cz.muni.pa165.banking.account.query.dto.Balance;
import cz.muni.pa165.banking.application.mapper.BalanceMapper;
import cz.muni.pa165.banking.application.service.BalanceServiceImpl;
import cz.muni.pa165.banking.domain.balance.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public boolean createNewBalance(String id) {
        if(! balanceService.addNewBalance(id)){
            throw new RuntimeException("Balance for person with id:" + id + " already exists.");
        };
        return balanceService.addNewBalance(id);
    }
}
