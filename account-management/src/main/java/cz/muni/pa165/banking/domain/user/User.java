package cz.muni.pa165.banking.domain.user;

import cz.muni.pa165.banking.domain.account.Account;
import cz.muni.pa165.banking.dto.DtoUserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

public class User {

    private final String id;
    @NotBlank(message = "Username must not be blank")
    private String username;
    @NotBlank(message = "Password must not be blank")
    private String password;
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email must not be blank")
    private String email;
    @NotBlank(message = "First name must not be blank")
    private String firstName;
    @NotBlank(message = "Last name must not be blank")
    private String lastName;
    @NotNull(message = "User type must not be null")
    private DtoUserType type;
    private Set<String> accounts = new HashSet<>();

    public User(String id, String username, String password, String email, String firstName, String lastName, DtoUserType type) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.type = type;
    }

    @Transactional
    public boolean addAccount(String accountId) {
        return accounts.add(accountId);
    }
    public Set<String> getAccounts() {
        return accounts;
    }

    @Transactional
    public boolean deleteAccount(String accountId) {
        return accounts.remove(accountId);
    }
    public String getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public DtoUserType getType() {
        return type;
    }
    public void setType(DtoUserType type) {
        this.type = type;
    }
}
