package cz.muni.pa165.banking.application.facade;
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
    @Autowired
    public BalanceFacade(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    public void createNewBalance(int id) {
        if(! balanceService.addNewBalance(id)){
            throw new RuntimeException("Balance for person with id:" + id + " already exists.");
        };
    }
}
