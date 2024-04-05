package cz.muni.pa165.banking.domain.report;

import cz.muni.pa165.banking.domain.transaction.Transaction;
import cz.muni.pa165.banking.domain.transaction.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class StatisticalReportTest {
    StatisticalReport report;
    @BeforeEach
    public void init(){
        Transaction tr1 = new Transaction(TransactionType.CREDIT, BigDecimal.ONE, OffsetDateTime.now(), new UUID(2, 2));
        Transaction tr2 = new Transaction(TransactionType.CREDIT, BigDecimal.TEN, OffsetDateTime.now(), new UUID(2, 2));
        Transaction tr3 = new Transaction(TransactionType.WITHDRAW, BigDecimal.ONE, OffsetDateTime.now(), new UUID(2, 2));
        Transaction tr5 = new Transaction(TransactionType.REFUND, BigDecimal.ONE, OffsetDateTime.now(), new UUID(2, 2));
        Transaction tr6 = new Transaction(TransactionType.DEPOSIT, BigDecimal.ONE, OffsetDateTime.now(), new UUID(2, 2));
        Transaction tr7 = new Transaction(TransactionType.CROSS_ACCOUNT_PAYMENT, BigDecimal.ONE, OffsetDateTime.now(), new UUID(2, 2));
        report = new StatisticalReport(List.of(tr1, tr2, tr3, tr5, tr6, tr7));
    }
    @Test
    public void whenGetCreditStatsThenStatsOfCreditTypeReturned(){
        //Act
        TransactionStatistics statistics = report.getCreditAmount();
        //Assert
        assertThat(statistics.getType()).isEqualTo(TransactionType.CREDIT);
        assertThat(statistics.getTimesIn()).isEqualTo(2);
    }
    @Test
    public void whenGetWithdrawStatsThenStatsOfWithdrawTypeReturned(){
        //Act
        TransactionStatistics statistics = report.getWithdrawalAmount();
        //Assert
        assertThat(statistics.getType()).isEqualTo(TransactionType.WITHDRAW);
        assertThat(statistics.getTimesIn()).isEqualTo(1);
    }
    @Test
    public void whenGetRefundStatsThenStatsOfRefundTypeReturned(){
        //Act
        TransactionStatistics statistics = report.getRefundAmount();
        //Assert
        assertThat(statistics.getType()).isEqualTo(TransactionType.REFUND);
    }
    @Test
    public void whenGetDepositStatsThenStatsOfDepositTypeReturned(){
        //Act
        TransactionStatistics statistics = report.getDepositAmount();
        //Assert
        assertThat(statistics.getType()).isEqualTo(TransactionType.DEPOSIT);
    }
    @Test
    public void whenGetCrossStatsThenStatsOfCrossTypeReturned(){
        //Act
        TransactionStatistics statistics = report.getCrossAccountAmount();
        //Assert
        assertThat(statistics.getType()).isEqualTo(TransactionType.CROSS_ACCOUNT_PAYMENT);
    }
    @Test
    public void whenGetAllStatsThenStatsOfAllTypesReturned(){
        //Act
        TransactionStatistics statistics = report.getTotalAmount();
        //Assert
        assertThat(statistics.getType()).isEqualTo(null);
        assertThat(statistics.getTimesIn()).isEqualTo(6);
    }
    @Test
    public void whenGetMinMaxAmountThenRightAmountReturned(){
        //Act
        BigDecimal amountMin = report.getAmountMin();
        BigDecimal amountMax = report.getAmountMax();
        //Assert
        assertThat(amountMin).isEqualTo(BigDecimal.ONE);
        assertThat(amountMax).isEqualTo(BigDecimal.TEN);
    }
}