package cz.muni.pa165.banking.application.facade;

import cz.muni.pa165.banking.account.management.dto.CreateUserRequest;
import cz.muni.pa165.banking.account.management.dto.GetUserRequest;
import cz.muni.pa165.banking.account.management.dto.UserType;
import cz.muni.pa165.banking.application.service.UserService;
import cz.muni.pa165.banking.account.management.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserFacade {
    private final UserService userService;
    @Autowired
    public UserFacade(UserService userService){
        this.userService = userService;
    }
    public User createUser(CreateUserRequest createUserRequest){
        return userService.createUser(createUserRequest);
    }
    public User getUser(GetUserRequest getUserRequest){
        return userService.getUser(getUserRequest);
    }
}
