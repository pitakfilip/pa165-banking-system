package cz.muni.pa165.banking.application.service;

import cz.muni.pa165.banking.domain.user.User;
import cz.muni.pa165.banking.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User createUser(User user){
        return userRepository.addUser(user);
    }

    public User findById(Long userId) throws Exception{
        return userRepository.findById(userId)
                .orElseThrow(() -> new Exception("User with id: " + userId + " not found"));
    }
}
