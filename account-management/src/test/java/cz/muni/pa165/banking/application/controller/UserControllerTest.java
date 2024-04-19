package cz.muni.pa165.banking.application.controller;

import cz.muni.pa165.banking.account.management.dto.NewUserDto;
import cz.muni.pa165.banking.account.management.dto.UserDto;
import cz.muni.pa165.banking.application.facade.UserFacade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @Mock
    private UserFacade userFacade;
    @InjectMocks
    private UserController userController;

    @Test
    void createUser_ValidRequest_ReturnsUser() {
        // Arrange
        NewUserDto newUserDto = new NewUserDto();
        UserDto createdUserDto = new UserDto();
        when(userFacade.createUser(any(NewUserDto.class))).thenReturn(createdUserDto);

        // Act
        ResponseEntity<UserDto> responseEntity = userController.createUser(newUserDto);

        // Assert
        assertNotNull(responseEntity.getBody());
        verify(userFacade).createUser(newUserDto);
    }

    @Test
    void getUser_ValidUserId_ReturnsUser() throws Exception {
        // Arrange
        Long userId = 1L;
        UserDto userDto = new UserDto();
        when(userFacade.findById(userId)).thenReturn(userDto);

        // Act
        ResponseEntity<UserDto> responseEntity = userController.findUserById(userId);

        // Assert
        assertNotNull(responseEntity.getBody());
        verify(userFacade).findById(userId);
    }

    @Test
    void getUser_InvalidUserId_ReturnsNull() throws Exception {
        // Arrange
        Long userId = 123L;
        when(userFacade.findById(userId)).thenReturn(null);

        // Act
        ResponseEntity<UserDto> responseEntity = userController.findUserById(userId);

        // Assert
        assertNull(responseEntity.getBody());
        verify(userFacade).findById(userId);
    }
}
