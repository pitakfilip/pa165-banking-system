package cz.muni.pa165.banking.application.facade;

import cz.muni.pa165.banking.account.management.dto.NewUserDto;
import cz.muni.pa165.banking.account.management.dto.UserDto;
import cz.muni.pa165.banking.application.mapper.DtoMapper;
import cz.muni.pa165.banking.application.service.UserService;
import cz.muni.pa165.banking.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
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

        // Act
        UserDto result = userFacade.createUser(newUserDto);

        // Assert
        assertEquals(userDto, result);
        verify(userService).createUser(any());
        verify(mapper).map(any(User.class));
    }

    @Test
    void getUser_ValidUserId_ReturnsUserDto() {
        // Arrange
        Long userId = 1L;
        UserDto userDto = new UserDto();
        when(userService.getUser(userId)).thenReturn(new User());
        when(mapper.map(any(User.class))).thenReturn(userDto);

        // Act
        UserDto result = userFacade.getUser(userId);

        // Assert
        assertEquals(userDto, result);
        verify(userService).getUser(userId);
        verify(mapper).map(any(User.class));
    }

    @Test
    void getUser_InvalidUserId_ReturnsNull() {
        // Arrange
        Long invalidUserId = 123L;
        when(userService.getUser(invalidUserId)).thenReturn(null);

        // Act
        UserDto result = userFacade.getUser(invalidUserId);

        // Assert
        assertNull(result);
        verify(userService).getUser(invalidUserId);
    }
}

