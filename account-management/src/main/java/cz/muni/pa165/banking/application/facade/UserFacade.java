package cz.muni.pa165.banking.application.facade;

import cz.muni.pa165.banking.application.service.UserService;
import cz.muni.pa165.banking.domain.account.Account;
import cz.muni.pa165.banking.domain.user.User;
import cz.muni.pa165.banking.dto.DtoUserType;
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
    public User createUser(String username, String password, String email, String firstName, String lastName, DtoUserType type){
        return userService.createUser(username, password, email, firstName, lastName, type);
    }
    public User getUser(String userId){
        return userService.getUser(userId);
    }
}
