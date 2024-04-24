package cz.muni.pa165.banking.domain.transaction.repository;

import cz.muni.pa165.banking.domain.balance.Balance;
import cz.muni.pa165.banking.domain.balance.repository.BalancesRepository;
import cz.muni.pa165.banking.domain.transaction.Transaction;
import cz.muni.pa165.banking.domain.transaction.TransactionType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Martin Mojzis
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository repository;
    @Autowired
    private BalancesRepository balancesRepository;

    @BeforeAll
    public static void initDb(@Autowired TransactionRepository repository, @Autowired BalancesRepository balancesRepository) {
        balancesRepository.save(new Balance("id1"));
        balancesRepository.save(new Balance("id2"));
    }

    @Test
    void findByBalance_called_returnsExpectedTransactions(){
        //Arrange
        Balance balance = balancesRepository.findById("id1").get();
        Transaction tr = balance.addTransaction(BigDecimal.TEN, TransactionType.CREDIT, new UUID(2,2));
        balancesRepository.save(balance);
        repository.save(tr);
        Balance balance2 = balancesRepository.findById("id2").get();

        //Act
        Collection<Transaction> result = repository.findByBalance(balance);
        Collection<Transaction> result2 = repository.findByBalance(balance2);

        //Assert
        assertTrue(result.contains(tr));
        assertTrue(result.size() == 1);
        assertTrue(result2.isEmpty());
    }
}