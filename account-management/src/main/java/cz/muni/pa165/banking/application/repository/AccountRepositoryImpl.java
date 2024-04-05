package cz.muni.pa165.banking.application.repository;


import cz.muni.pa165.banking.domain.account.Account;
import cz.muni.pa165.banking.domain.account.repository.AccountRepository;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.Map;

@Repository
public class AccountRepositoryImpl implements AccountRepository {
    
    private Map<String, Account> accounts = new HashMap<>();

    public boolean addAccount(Account account) {
        if (accounts.get(account.getId()) != null){
            return false;
        }
        accounts.put(account.getId(), account);
        return true;
    }
    
    public Account getById(String id) {
        return accounts.get(id);
    }

}
