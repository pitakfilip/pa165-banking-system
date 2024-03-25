package cz.muni.pa165.banking.application.facade;
import cz.muni.pa165.banking.application.service.BalanceService;
import cz.muni.pa165.banking.domain.balance.Balance;
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

}
