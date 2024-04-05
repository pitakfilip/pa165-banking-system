package cz.muni.pa165.banking.application.service;

import cz.muni.pa165.banking.domain.user.User;
import cz.muni.pa165.banking.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void createUser_ValidUser_ReturnsUser() {
        // Arrange
        User user = new User();
        when(userRepository.addUser(user)).thenReturn(user);

        // Act
        User result = userService.createUser(user);

        // Assert
        assertEquals(user, result);
        verify(userRepository).addUser(user);
    }

    @Test
    void getUser_ValidUserId_ReturnsUser() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        when(userRepository.getById(userId)).thenReturn(user);

        // Act
        User result = userService.getUser(userId);

        // Assert
        assertEquals(user, result);
        verify(userRepository).getById(userId);
    }

    @Test
    void getUser_InvalidUserId_ReturnsNull() {
        // Arrange
        Long userId = 1L;
        when(userRepository.getById(userId)).thenReturn(null);

        // Act & Assert
        assertNull(userService.getUser(userId));
        verify(userRepository).getById(userId);
    }
}

