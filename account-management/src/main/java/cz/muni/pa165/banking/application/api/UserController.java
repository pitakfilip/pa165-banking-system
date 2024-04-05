package cz.muni.pa165.banking.application.api;

import cz.muni.pa165.banking.account.management.UserApi;
import cz.muni.pa165.banking.account.management.dto.NewUserDto;
import cz.muni.pa165.banking.account.management.dto.UserDto;
import cz.muni.pa165.banking.application.facade.UserFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController implements UserApi{

    private final UserFacade userFacade;

    public UserController(UserFacade userFacade){
        this.userFacade = userFacade;
    }

    @Override
    public ResponseEntity<UserDto> createUser(NewUserDto newUserDto) {
        return new ResponseEntity<>(userFacade.createUser(newUserDto), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<UserDto> getUser(Long userId) {
        return ResponseEntity.ok(userFacade.getUser(userId));
    }    
    
}
