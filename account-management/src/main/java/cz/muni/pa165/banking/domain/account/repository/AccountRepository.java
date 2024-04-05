package cz.muni.pa165.banking.domain.account.repository;

import cz.muni.pa165.banking.domain.account.Account;

public interface AccountRepository {
    
    /**
     * Adds a new account to the repository.
     *
     * @param account the account to add
     */
    Account addAccount(Account account);

    /**
     * Retrieves an account by its ID.
     *
     * @param id the ID of the account to retrieve
     * @return the account with the specified ID, or null if no such account exists
     */
    Account getById(Long id);

    Account getByAccountNumber(String accountNumber);
    
}
