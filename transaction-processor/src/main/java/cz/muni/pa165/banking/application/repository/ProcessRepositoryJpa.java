package cz.muni.pa165.banking.application.repository;

import cz.muni.pa165.banking.domain.process.Process;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessRepositoryJpa extends JpaRepository<Process, String> {
    
    // TODO find podla statusu
    
    // TODO update zaseknute procesy starsie nez e.g. tyzden a zmenit na failed + nastavit message nejaky
    
}
