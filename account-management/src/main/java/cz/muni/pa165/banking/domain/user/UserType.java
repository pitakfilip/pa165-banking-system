package cz.muni.pa165.banking.domain.user;

import com.fasterxml.jackson.annotation.JsonValue;

public enum UserType {
    REGULAR("REGULAR"),
    EMPLOYEE("EMPLOYEE");

    private String value;

    private UserType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return this.value;
    }

    public String toString() {
        return String.valueOf(this.value);
    }

    public static UserType fromValue(String value) {
        for (UserType userType : UserType.values()) {
            if (userType.getValue().equalsIgnoreCase(value)) {
                return userType;
            }
        }
        throw new IllegalArgumentException("Invalid UserType value: " + value);
    }
}
