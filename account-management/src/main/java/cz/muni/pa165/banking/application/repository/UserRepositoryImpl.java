package cz.muni.pa165.banking.application.repository;

import cz.muni.pa165.banking.domain.user.User;
import cz.muni.pa165.banking.domain.user.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepositoryImpl implements UserRepository {
    
    private Long sequencer = 1L;
    private Map<Long, User> users = new HashMap<>();

    public User addUser(User user) {
        user.setId(sequencer);
        users.put(sequencer++, user);
        return user;
    }

    @Override
    public User getById(Long id) {
        return users.get(id);
    }
}
