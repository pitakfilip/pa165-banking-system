package cz.muni.pa165.banking.domain.user.repository;

import cz.muni.pa165.banking.domain.user.User;

public interface UserRepository {
    
    /**
     * Adds a new user to the repository.
     *
     * @param user the user to add
     */
    User addUser(User user);

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user to retrieve
     * @return the user with the specified ID, or null if no such user exists
     */
    User getById(Long id);
    
}
