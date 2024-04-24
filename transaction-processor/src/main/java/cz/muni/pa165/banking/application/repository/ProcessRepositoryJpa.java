package cz.muni.pa165.banking.application.repository;

import cz.muni.pa165.banking.domain.process.Process;
import cz.muni.pa165.banking.domain.process.status.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface ProcessRepositoryJpa extends JpaRepository<Process, String> {
    
    @Query("SELECT p FROM Process p WHERE p.currentStatus.status = :status")
    List<Process> findAllWithStatus(@Param("status") Status status);
    
    @Query("SELECT p FROM Process p WHERE p.currentStatus.status = :status AND p.currentStatus.when <= :date")
    List<Process> findByStatusAndDateBeforeEqual(@Param("status") Status status, @Param("date") Instant date);

}
