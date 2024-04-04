package cz.muni.pa165.banking.application.facade;

import cz.muni.pa165.banking.account.management.dto.NewUserDto;
import cz.muni.pa165.banking.account.management.dto.UserDto;
import cz.muni.pa165.banking.application.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserFacade {
    
    private final UserService userService;

    public UserFacade(UserService userService){
        this.userService = userService;
    }

    public UserDto createUser(NewUserDto newUserDto){
        User newUser = userService.createUser(mapper.mapFromDto(newUserDto));
        return mapper.mapToDto(newUser);
    }

    public UserDto getUser(Long userId){
        return mapper.mapToDto(userService.getUser(userId));
    }

}
