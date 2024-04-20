package cz.muni.pa165.banking.application.controller;

import cz.muni.pa165.banking.account.management.UserApi;
import cz.muni.pa165.banking.account.management.dto.NewUserDto;
import cz.muni.pa165.banking.account.management.dto.UserDto;
import cz.muni.pa165.banking.application.facade.UserFacade;
import cz.muni.pa165.banking.exception.EntityNotFoundException;
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
    public ResponseEntity<UserDto> findUserById(Long userId) {
        UserDto user;
        try{
            user = userFacade.findById(userId);
        }
        catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(user);
    }    
    
}
