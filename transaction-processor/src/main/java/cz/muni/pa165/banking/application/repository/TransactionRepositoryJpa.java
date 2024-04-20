package cz.muni.pa165.banking.application.repository;

import cz.muni.pa165.banking.domain.process.ProcessTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepositoryJpa extends JpaRepository<ProcessTransaction, String> {
}
