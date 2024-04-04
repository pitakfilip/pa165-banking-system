package cz.muni.pa165.banking.application.repository;

import cz.muni.pa165.banking.account.management.dto.Account;
import cz.muni.pa165.banking.domain.account.repository.AccountRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.Map;

@Repository
public class AccountRepositoryImpl implements AccountRepository {
    private Map<String, Account> accounts = new HashMap<>();

    @Transactional
    public boolean addAccount(Account account) {
        if (accounts.get(account.getId()) != null){
            return false;
        }
        accounts.put(account.getId(), account);
        return true;
    }
    @Transactional
    public Account getById(String id) {
        return accounts.get(id);
    }
}
