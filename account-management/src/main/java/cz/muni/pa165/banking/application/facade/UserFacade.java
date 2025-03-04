package cz.muni.pa165.banking.application.facade;

import cz.muni.pa165.banking.account.management.dto.NewUserDto;
import cz.muni.pa165.banking.account.management.dto.UserDto;
import cz.muni.pa165.banking.application.mapper.DtoMapper;
import cz.muni.pa165.banking.application.service.UserService;
import cz.muni.pa165.banking.domain.user.User;
import cz.muni.pa165.banking.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserFacade {
    
    private final UserService userService;
    private final DtoMapper mapper;

    public UserFacade(UserService userService, DtoMapper mapper){
        this.userService = userService;
        this.mapper = mapper;
    }

    public UserDto createUser(NewUserDto newUserDto){
        User newUser = userService.createUser(mapper.map(newUserDto));
        return mapper.map(newUser);
    }

    public UserDto findById(Long userId) throws EntityNotFoundException {
        return mapper.map(userService.findById(userId));
    }

}
