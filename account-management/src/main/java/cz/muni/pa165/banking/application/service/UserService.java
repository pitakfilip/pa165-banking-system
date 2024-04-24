package cz.muni.pa165.banking.application.service;

import cz.muni.pa165.banking.domain.user.User;
import cz.muni.pa165.banking.domain.user.repository.UserRepository;
import cz.muni.pa165.banking.exception.EntityNotFoundException;
import jakarta.persistence.EntityExistsException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User createUser(User user) throws EntityExistsException {
        if (userRepository.existsById(user.getId())){
            throw new EntityExistsException("User with id " + user.getId() + " already exists");
        }
        return userRepository.save(user);
    }

    public User findById(Long userId) throws EntityNotFoundException {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id: " + userId + " not found"));
    }
}
