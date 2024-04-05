package cz.muni.pa165.banking.application.repository;

import cz.muni.pa165.banking.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class UserRepositoryImplTest {

    private UserRepositoryImpl userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepositoryImpl();
    }

    @Test
    void addUser_ValidUser_ReturnsUser() {
        // Arrange
        User user = new User();

        // Act
        User result = userRepository.addUser(user);

        // Assert
        assertEquals(user, result);
    }

    @Test
    void getById_ExistingId_ReturnsUser() {
        // Arrange
        User user = new User();
        userRepository.addUser(user);
        Long id = user.getId();

        // Act
        User result = userRepository.getById(id);

        // Assert
        assertEquals(user, result);
    }

    @Test
    void getById_NonExistingId_ReturnsNull() {
        // Arrange
        Long nonExistingId = 123L;

        // Act
        User result = userRepository.getById(nonExistingId);

        // Assert
        assertNull(result);
    }
}
