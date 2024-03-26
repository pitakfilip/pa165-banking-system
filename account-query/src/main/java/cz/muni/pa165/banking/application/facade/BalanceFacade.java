package cz.muni.pa165.banking.application.facade;
import cz.muni.pa165.banking.application.service.BalanceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * @author Martin Mojzis
 */
@Service
public class BalanceFacade {
    private final BalanceServiceImpl balanceService;
    @Autowired
    public BalanceFacade(BalanceServiceImpl balanceService) {
        this.balanceService = balanceService;
    }
}
