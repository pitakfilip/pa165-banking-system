package cz.muni.pa165.banking.application.service;

import cz.muni.pa165.banking.domain.user.User;
import cz.muni.pa165.banking.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void createUser_ValidUser_ReturnsUser() {
        // Arrange
        User user = new User();
        when(userRepository.save(user)).thenReturn(user);

        // Act
        User result = userService.createUser(user);

        // Assert
        assertEquals(user, result);
        verify(userRepository).save(user);
    }

    @Test
    void getUser_ValidUserId_ReturnsUser(){
        // Arrange
        Long userId = 1L;
        User user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        User result = userService.findById(userId);

        // Assert
        assertEquals(user, result);
        verify(userRepository).findById(userId);
    }

    @Test
    void getUser_InvalidUserId_ThrowsException() {
        // Arrange
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(Exception.class, () -> {
            userService.findById(userId);
        });

        verify(userRepository).findById(userId);
    }
}

