package cz.muni.pa165.banking.domain.account;

public class Account {
    
    private String accountNumber;

    @Deprecated // hibernate
    public Account() {}
    
    public Account(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
    
    @Deprecated // hibernate
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
