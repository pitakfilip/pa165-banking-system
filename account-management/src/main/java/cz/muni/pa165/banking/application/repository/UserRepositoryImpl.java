package cz.muni.pa165.banking.application.repository;

import cz.muni.pa165.banking.domain.user.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class UserRepositoryImpl implements UserRepository {
    
    private Map<String, User> users = new HashMap<>();

    public boolean addUser(User user) {
        if (users.get(user.getId()) != null){
            return false;
        }
        users.put(user.getId(), user);
        return true;
    }

    public User getById(String id) {
        return users.get(id);
    }

}
