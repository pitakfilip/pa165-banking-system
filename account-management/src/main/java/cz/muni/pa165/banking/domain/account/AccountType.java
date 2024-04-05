package cz.muni.pa165.banking.domain.account;

import com.fasterxml.jackson.annotation.JsonValue;
import cz.muni.pa165.banking.domain.user.UserType;

public enum AccountType {
    SPENDING("SPENDING"),
    SAVING("SAVING"),
    CREDIT("CREDIT"),;

    private String value;

    private AccountType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return this.value;
    }

    public String toString() {
        return String.valueOf(this.value);
    }

    public static AccountType fromValue(String value) {
        for (AccountType accountType : AccountType.values()) {
            if (accountType.getValue().equalsIgnoreCase(value)) {
                return accountType;
            }
        }
        throw new IllegalArgumentException("Invalid UserType value: " + value);
    }
}
