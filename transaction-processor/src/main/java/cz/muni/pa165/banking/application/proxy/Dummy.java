package cz.muni.pa165.banking.application.proxy;

import cz.muni.pa165.banking.account.query.CustomerServiceApi;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Dummy {
    
    @Autowired
    private CustomerServiceApi customerServiceApi;
    
    @PostConstruct
    public void test() {
        var jj = customerServiceApi.getBalance("id1");
        System.out.println("jozo");
    }
    
}
