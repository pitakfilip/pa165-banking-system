package cz.muni.pa165.banking.domain.account.repository;

import cz.muni.pa165.banking.domain.account.Account;
import cz.muni.pa165.banking.domain.scheduled.ScheduledPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    
    /**
     * Adds a new account to the repository.
     *
     * @param account the account to add
     * @return newly created account
     */
    default Account addAccount(Account account){
        this.save(account);
        return account;
    }

    /**
     * Retrieves an account by its ID.
     *
     * @param id the ID of the account to retrieve
     * @return the account with the specified ID, or null if no such account exists
     */
    @Query("SELECT a FROM Account a where a.id = :id")
    Optional<Account> findById(Long id);

    @Query("SELECT a FROM Account a where a.accountNumber = :accountNumber")
    Optional<Account> findByNumber(String accountNumber);

    @Query("SELECT a FROM Account a where a.userId = :userId")
    List<Account> findAllByUserId(Long userId);

    @Query("SELECT p FROM ScheduledPayment p where p.senderAccountId = :accountId")
    List<ScheduledPayment> findScheduledPaymentsById(Long accountId);
}
