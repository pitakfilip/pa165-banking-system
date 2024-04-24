package cz.muni.pa165.banking.application.repository;

import cz.muni.pa165.banking.domain.process.ProcessTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepositoryJpa extends JpaRepository<ProcessTransaction, UUID> {
}
