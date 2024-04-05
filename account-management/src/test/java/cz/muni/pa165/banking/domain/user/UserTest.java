package cz.muni.pa165.banking.domain.user;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    public void testGettersAndSetters() {
        // Arrange
        User user = new User();
        Long id = 1L;
        String email = "john.doe@example.com";
        String password = "password";
        String firstName = "John";
        String lastName = "Doe";
        UserType userType = UserType.EMPLOYEE;
        List<String> accounts = new ArrayList<>();

        // Act
        user.setId(id);
        user.setEmail(email);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUserType(userType);
        user.setAccounts(accounts);

        // Assert
        assertEquals(id, user.getId());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());
        assertEquals(userType, user.getUserType());
        assertEquals(accounts, user.getAccounts());
    }

    @Test
    public void testDefaultConstructor() {
        // Act
        User user = new User();

        // Assert
        assertNull(user.getId());
        assertNull(user.getEmail());
        assertNull(user.getPassword());
        assertNull(user.getFirstName());
        assertNull(user.getLastName());
        assertNull(user.getUserType());
        assertNull(user.getAccounts());
    }
}