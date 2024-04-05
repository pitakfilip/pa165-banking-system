package cz.muni.pa165.banking.application.mapper;

import cz.muni.pa165.banking.account.query.dto.Transaction;
import cz.muni.pa165.banking.domain.transaction.TransactionType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;


class BalanceMapperTest {
    BalanceMapper balanceMapper = new BalanceMapperImpl();
    @Test
    void mapTypeOutTest(){
        //Arrange
        cz.muni.pa165.banking.account.query.dto.TransactionType type =
                cz.muni.pa165.banking.account.query.dto.TransactionType.CREDIT;

        //Act
        TransactionType type2 = balanceMapper.mapTypeOut(type);
        //Assert
        assertThat(type2).isEqualTo(TransactionType.CREDIT);
        assertThat(balanceMapper.mapTypeOut(null)).isNull();
    }
    @Test
    void mapTypeInTest(){
        //Arrange
        TransactionType type = TransactionType.CREDIT;

        //Act
        cz.muni.pa165.banking.account.query.dto.TransactionType type2 = balanceMapper.mapTypeIn(type);
        //Assert
        assertThat(type2).isEqualTo(cz.muni.pa165.banking.account.query.dto.TransactionType.CREDIT);
        assertThat(balanceMapper.mapTypeIn(null)).isNull();
    }
}