package cz.muni.pa165.banking.application.facade;

import cz.muni.pa165.banking.account.management.dto.NewUserDto;
import cz.muni.pa165.banking.account.management.dto.UserDto;
import cz.muni.pa165.banking.application.mapper.DtoMapper;
import cz.muni.pa165.banking.application.service.UserService;
import cz.muni.pa165.banking.domain.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserFacadeTest {

    @Mock
    private UserService userService;

    @Mock
    private DtoMapper mapper;

    @InjectMocks
    private UserFacade userFacade;

    @Test
    void createUser_ValidRequest_ReturnsUserDto() {
        // Arrange
        NewUserDto newUserDto = new NewUserDto();
        UserDto userDto = new UserDto();
        when(userService.createUser(any())).thenReturn(new User());
        when(mapper.map(any(User.class))).thenReturn(userDto);
        when(mapper.map(any(NewUserDto.class))).thenReturn(new User());

        // Act
        UserDto result = userFacade.createUser(newUserDto);

        // Assert
        assertEquals(userDto, result);
        verify(userService).createUser(any());
        verify(mapper).map(any(User.class));
    }

    @Test
    void getUser_ValidUserId_ReturnsUserDto(){
        // Arrange
        Long userId = 1L;
        UserDto userDto = new UserDto();
        when(userService.findById(userId)).thenReturn(new User());
        when(mapper.map(any(User.class))).thenReturn(userDto);

        // Act
        UserDto result = userFacade.findById(userId);

        // Assert
        assertEquals(userDto, result);
        verify(userService).findById(userId);
        verify(mapper).map(any(User.class));
    }

    @Test
    void getUser_InvalidUserId_ReturnsNull(){
        // Arrange
        Long invalidUserId = 123L;
        when(userService.findById(invalidUserId)).thenReturn(null);

        // Act
        UserDto result = userFacade.findById(invalidUserId);

        // Assert
        assertNull(result);
        verify(userService).findById(invalidUserId);
    }
}

