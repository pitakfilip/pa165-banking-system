package cz.muni.pa165.banking.application.service;
import cz.muni.pa165.banking.application.repository.AccountRepositoryImpl;
import cz.muni.pa165.banking.application.repository.UserRepositoryImpl;
import cz.muni.pa165.banking.domain.account.Account;
import cz.muni.pa165.banking.domain.user.User;
import cz.muni.pa165.banking.dto.DtoUserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    private final UserRepositoryImpl userRepository;
    @Autowired
    public UserService(UserRepositoryImpl userRepository){
        this.userRepository = userRepository;
    }

    public User createUser(String username, String password, String email, String firstName, String lastName, DtoUserType type){
        User newUser = new User(UUID.randomUUID().toString(), username, password, email, firstName, lastName, type);
        while (!userRepository.addUser(newUser)) {
            newUser= new User(UUID.randomUUID().toString(), username, password, email, firstName, lastName, type);
        }
        return newUser;
    }
    public User getUser(String userId){
        return userRepository.getById(userId);
    }
}
