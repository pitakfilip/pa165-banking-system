package cz.muni.pa165.banking.application.repository;

import cz.muni.pa165.banking.account.management.dto.User;
import cz.muni.pa165.banking.domain.user.repository.UserRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private Map<String, User> users = new HashMap<>();

    @Transactional
    public boolean addUser(User user) {
        if (users.get(user.getId()) != null){
            return false;
        }
        users.put(user.getId(), user);
        return true;
    }
    @Transactional
    public User getById(String id) {
        return users.get(id);
    }
}
