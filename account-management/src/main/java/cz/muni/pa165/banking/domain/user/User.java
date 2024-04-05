package cz.muni.pa165.banking.domain.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

public class User {
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private UserType userType;
    private @Valid List<String> accounts;

    public User() {
    }

    public User(Long id, String email, String password, String firstName, String lastName, UserType userType) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userType = userType;
        this.accounts = new ArrayList<>();
    }

    @JsonProperty("id")
    public @NotNull Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty("email")
    public @NotNull String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("password")
    public @NotNull String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonProperty("firstName")
    public @NotNull String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @JsonProperty("lastName")
    public @NotNull String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @JsonProperty("userType")
    public @NotNull @Valid UserType getUserType() {
        return this.userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    @JsonProperty("accounts")
    public List<String> getAccounts() {
        return this.accounts;
    }

    public void setAccounts(List<String> accounts) {
        this.accounts = accounts;
    }

    public void addAccountsItem(String accountsItem) {
        this.accounts.add(accountsItem);
    }
}
