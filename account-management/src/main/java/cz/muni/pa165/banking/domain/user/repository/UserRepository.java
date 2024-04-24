package cz.muni.pa165.banking.domain.user.repository;

import cz.muni.pa165.banking.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {}
