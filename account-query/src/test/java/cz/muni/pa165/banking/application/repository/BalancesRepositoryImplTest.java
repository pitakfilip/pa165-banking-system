//package cz.muni.pa165.banking.application.repository;
//
//import cz.muni.pa165.banking.application.exception.NotFoundAccountException;
//import cz.muni.pa165.banking.domain.balance.Balance;
//import cz.muni.pa165.banking.domain.transaction.TransactionType;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.math.BigDecimal;
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//
//
//class BalancesRepositoryImplTest {
//    BalancesRepository repository;
//    @BeforeEach
//    public void init(){
//        repository = new BalancesRepository();
//        repository.addBalance("id1");
//        repository.addBalance("id2");
//    }
//
//    @Test
//    void findById_accountNotExists_returnsEmpty()
//    {
//        //Act
//        Optional<Balance> result = repository.findById("id3");
//
//        //Assert
//        assertThat(result).isEqualTo(Optional.empty());
//    }
//    @Test
//    void findById_accountExists_returnsBalance()
//    {
//        //Act
//        Optional<Balance> result = repository.findById("id2");
//
//        //Assert
//        assertThat(result).isNotNull();
//        assertThat(result.get().getAmount()).isEqualTo(BigDecimal.ZERO);
//        assertThat(result.get().getAccountId()).isEqualTo("id2");
//    }
//    @Test
//    void getAllIds_returnsAllIds(){
//        //Act
//        List<String> ids = repository.getAllIds();
//        //Assert
//        assertThat(ids).isNotNull();
//        assertThat(ids.size()).isEqualTo(2);
//        assertThat(ids.contains("id1")).isEqualTo(true);
//    }
//}