package cz.muni.pa165.banking.application.service;

import cz.muni.pa165.banking.account.management.dto.UserDto;
import cz.muni.pa165.banking.application.repository.UserRepositoryImpl;
import cz.muni.pa165.banking.domain.user.User;
import cz.muni.pa165.banking.domain.user.UserType;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepositoryImpl userRepository;

    public UserService(UserRepositoryImpl userRepository){
        this.userRepository = userRepository;
    }

    public User createUser(User user){
        userRepository.addUser(user);
        return user;
    }

    public User getUser(Long userId){
        return userRepository.getById(userId);
    }
}
