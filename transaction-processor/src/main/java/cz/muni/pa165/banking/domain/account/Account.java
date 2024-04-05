package cz.muni.pa165.banking.domain.account;

public class Account {
    
    private final String accountNumber;

    public Account(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
}
