package cz.muni.pa165.banking.domain.report;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThat;

class TransactionStatisticsTest {
    TransactionStatistics statistics;
    @BeforeEach
    public void init(){
        statistics = new TransactionStatistics();
        statistics.AddAmount(BigDecimal.TEN);
        statistics.AddAmount(BigDecimal.TEN);
        statistics.AddAmount(BigDecimal.ONE);
        statistics.AddAmount(BigDecimal.valueOf(-10));
    }
    @Test
    public void whenAddAmountThenAmountHigher(){
        //Act
        BigDecimal amountBefore = statistics.getAmountIn();
        statistics.AddAmount(BigDecimal.ONE);
        //Assert
        assertThat(statistics.getAmountIn()).isEqualTo(amountBefore.add(BigDecimal.ONE));
    }
    @Test
    public void whenLessenAmountThenAmountOutHigher(){
        //Act
        BigDecimal amountBefore = statistics.getAmountOut();
        statistics.AddAmount(BigDecimal.valueOf(-10));
        //Assert
        assertThat(statistics.getAmountOut()).isEqualTo(amountBefore.add(BigDecimal.valueOf(-10)));
    }
    @Test
    public void whenAddAmountThenCountInHigher(){
        //Act
        int timesBefore = statistics.getTimesIn();
        statistics.AddAmount(BigDecimal.ONE);
        //Assert
        assertThat(statistics.getTimesIn()).isEqualTo(timesBefore + 1);
    }
    @Test
    public void whenLessenAmountThenCountOutHigher(){
        //Act
        int timesBefore = statistics.getTimesOut();
        statistics.AddAmount(BigDecimal.valueOf(-10));
        //Assert
        assertThat(statistics.getTimesOut()).isEqualTo(timesBefore + 1);
    }
}