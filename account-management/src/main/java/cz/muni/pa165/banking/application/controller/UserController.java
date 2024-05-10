package cz.muni.pa165.banking.application.controller;

import cz.muni.pa165.banking.account.management.UserApi;
import cz.muni.pa165.banking.account.management.dto.NewUserDto;
import cz.muni.pa165.banking.account.management.dto.UserDto;
import cz.muni.pa165.banking.application.facade.UserFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController implements UserApi{

    private final UserFacade userFacade;

    public UserController(UserFacade userFacade){
        this.userFacade = userFacade;
    }

    @Override
    @PreAuthorize("hasAnyAuthority('SCOPE_test_2', 'SCOPE_test_3')")
    public ResponseEntity<UserDto> createUser(NewUserDto newUserDto) {
        return new ResponseEntity<>(userFacade.createUser(newUserDto), HttpStatus.CREATED);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('SCOPE_test_2', 'SCOPE_test_3')")
    public ResponseEntity<UserDto> findUserById(Long userId) {
        UserDto user = userFacade.findById(userId);
        return ResponseEntity.ok(user);
    }    
    
}
