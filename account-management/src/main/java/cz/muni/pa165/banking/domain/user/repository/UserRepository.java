package cz.muni.pa165.banking.domain.user.repository;

import cz.muni.pa165.banking.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Adds a new user to the repository.
     *
     * @param user the user to add
     * @return newly created user
     */
    default User addUser(User user){
        return saveAndFlush(user);
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user to retrieve
     * @return the user with the specified ID, or null if no such user exists
     */
    @Query("SELECT u FROM User u where u.id = :id")
    Optional<User> findById(Long id);
    
}
