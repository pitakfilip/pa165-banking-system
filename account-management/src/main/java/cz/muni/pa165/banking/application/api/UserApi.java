package cz.muni.pa165.banking.application.api;

import cz.muni.pa165.banking.application.facade.UserFacade;
import cz.muni.pa165.banking.domain.account.Account;
import cz.muni.pa165.banking.domain.user.User;
import cz.muni.pa165.banking.dto.DtoUserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class UserApi {
    private final UserFacade userFacade;
    @Autowired
    public UserApi(UserFacade userFacade){
        this.userFacade = userFacade;
    }
    public User createUser(String username, String password, String email, String firstName, String lastName, DtoUserType type){
        return userFacade.createUser(username, password, email, firstName, lastName, type);
    }
    public User getUser(String userId){
        return userFacade.getUser(userId);
    }
}
