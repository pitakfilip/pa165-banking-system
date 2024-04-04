package cz.muni.pa165.banking.application.api;

import cz.muni.pa165.banking.account.management.UserApi;
import cz.muni.pa165.banking.account.management.dto.CreateUserRequest;
import cz.muni.pa165.banking.account.management.dto.GetUserRequest;
import cz.muni.pa165.banking.account.management.dto.User;
import cz.muni.pa165.banking.account.management.dto.UserType;
import cz.muni.pa165.banking.application.facade.UserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController implements UserApi{
    private final UserFacade userFacade;
    @Autowired
    public UserController(UserFacade userFacade){
        this.userFacade = userFacade;
    }

    @Override
    public ResponseEntity<User> createUser(CreateUserRequest createUserRequest) {
        return ResponseEntity.ok(userFacade.createUser(createUserRequest));
    }

    @Override
    public ResponseEntity<User> getUser(GetUserRequest getUserRequest) {
        return ResponseEntity.ok(userFacade.getUser(getUserRequest));
    }
}
