package cz.muni.pa165.banking.application.repository;

import cz.muni.pa165.banking.domain.balance.Balance;
import cz.muni.pa165.banking.domain.balance.repository.BalancesRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@Transactional
@DataJpaTest
class BalancesRepositoryTest {

    @Autowired
    BalancesRepository balancesRepository;
    @BeforeEach
    public void init(){
        balancesRepository.addBalance("id1");
        balancesRepository.addBalance("id2");
    }

    @Test
    void findById_accountNotExists_returnsEmpty()
    {
        //Act
        Optional<Balance> result = balancesRepository.findById("id3");

        //Assert
        assertThat(result).isEqualTo(Optional.empty());
    }
    @Test
    void findById_accountExists_returnsBalance()
    {
        //Act
        Optional<Balance> result = balancesRepository.findById("id2");

        //Assert
        assertThat(result).isNotNull();
        assertThat(result.get().getAmount()).isEqualTo(BigDecimal.ZERO);
        assertThat(result.get().getAccountId()).isEqualTo("id2");
    }
    @Test
    void getAllIds_returnsAllIds(){
        //Act
        List<String> ids = balancesRepository.getAllIds();
        //Assert
        assertThat(ids).isNotNull();
        assertThat(ids.size()).isEqualTo(2);
        assertThat(ids.contains("id1")).isEqualTo(true);
    }
}