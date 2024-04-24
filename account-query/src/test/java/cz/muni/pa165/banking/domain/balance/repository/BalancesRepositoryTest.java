package cz.muni.pa165.banking.domain.balance.repository;

import cz.muni.pa165.banking.domain.balance.Balance;
import cz.muni.pa165.banking.domain.transaction.TransactionType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Martin Mojzis
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
class BalancesRepositoryTest {

    @Autowired
    private BalancesRepository repository;

    @BeforeAll
    public static void initDb(@Autowired BalancesRepository repository) {
        Balance balance = new Balance("id1");

        balance.addTransaction(BigDecimal.TEN, TransactionType.CREDIT, new UUID(2,2));
        repository.save(balance);

        repository.save(balance);
        repository.save(new Balance("id2"));

    }

    @Test
    void findById_exists_returnsBalance() {
        Optional<Balance> balance = repository.findById("id1");
        assertEquals(balance.get().getAccountId(), "id1");
        assertEquals(balance.get().getAmount().byteValueExact(), BigDecimal.TEN.byteValueExact());
    }

    @Test
    void getAllIds_returnsAddIds() {
        List<String> result = repository.getAllIds();
        assertTrue(result.contains("id1"));
        assertTrue(result.contains("id2"));
    }
}