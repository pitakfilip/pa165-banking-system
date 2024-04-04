package cz.muni.pa165.banking.application.service;

import cz.muni.pa165.banking.account.management.dto.CreateUserRequest;
import cz.muni.pa165.banking.account.management.dto.GetUserRequest;
import cz.muni.pa165.banking.account.management.dto.UserType;
import cz.muni.pa165.banking.application.repository.UserRepositoryImpl;
import cz.muni.pa165.banking.account.management.dto.User;
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

    public User createUser(CreateUserRequest createUserRequest){
        String email = createUserRequest.getEmail();
        String password = createUserRequest.getPassword();
        String firstName = createUserRequest.getFirstName();
        String lastName = createUserRequest.getLastName();
        UserType type = createUserRequest.getType();

        User newUser = new User(UUID.randomUUID().toString(), email, password, firstName, lastName, type);
        while (!userRepository.addUser(newUser)) {
            newUser = new User(UUID.randomUUID().toString(), email, password, firstName, lastName, type);
        }
        return newUser;
    }

    public User getUser(GetUserRequest getUserRequest){
        String userId = getUserRequest.getUserId();
        return userRepository.getById(userId);
    }
}
