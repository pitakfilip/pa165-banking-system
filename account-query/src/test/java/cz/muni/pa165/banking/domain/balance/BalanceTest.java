package cz.muni.pa165.banking.domain.balance;

import cz.muni.pa165.banking.domain.report.StatisticalReport;
import cz.muni.pa165.banking.domain.transaction.Transaction;
import cz.muni.pa165.banking.domain.transaction.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

class BalanceTest {
    Balance balance;
    @BeforeEach
    public void init(){
        balance = new Balance("examlpeId");
    }
    @Test
    public void whenIdRequestedRightIdReturned(){
        //Assert
        assertThat(balance.getAccountId()).isEqualTo("examlpeId");
    }
    @Test
    public void whenAddTransactionThenStatusChangesPositive(){
        //Act
        balance.addTransaction(BigDecimal.TEN, TransactionType.CREDIT, new UUID(2, 2));
        //Assert
        assertThat(balance.getAmount()).isEqualTo(BigDecimal.TEN);
    }
    @Test
    public void whenAddTransactionThenStatusChangesNegative(){
        //Act
        balance.addTransaction(BigDecimal.valueOf(-10), TransactionType.CREDIT, new UUID(2, 2));
        //Assert
        assertThat(balance.getAmount()).isEqualTo(BigDecimal.valueOf(-10));
    }
    @Test
    public void whenAddTransactionThenTransactionIsSaved(){
        //Act
        UUID id = new UUID(2, 2);
        balance.addTransaction(BigDecimal.TEN, TransactionType.CREDIT, id);
        Transaction transaction = balance.getTransaction(id);
        //Assert
        assertThat(transaction.getAmount()).isEqualTo(BigDecimal.TEN);
        assertThat(transaction.getType()).isEqualTo(TransactionType.CREDIT);
        assertThat(transaction.getProcessId()).isEqualTo(id);
    }
    @Test
    public void whenAddTransactionThenGetTransactionsReturnsIt(){
        //Act
        UUID id = new UUID(2, 2);
        balance.addTransaction(BigDecimal.TEN, TransactionType.CREDIT, id);
        Transaction transaction = balance.getTransactions().get(0);
        //Assert
        assertThat(transaction.getAmount()).isEqualTo(BigDecimal.TEN);
        assertThat(transaction.getType()).isEqualTo(TransactionType.CREDIT);
        assertThat(transaction.getProcessId()).isEqualTo(id);
    }
    @Test
    public void whenAddTransactionThenTransactionExistsReturnsTrue(){
        //Act
        UUID id = new UUID(2, 2);
        balance.addTransaction(BigDecimal.TEN, TransactionType.CREDIT, id);
        //Assert
        assertThat(balance.transactionExists(id)).isEqualTo(true);
    }
    @Test
    public void whenAddTransactionThenTransactionExistsWithWrongIdReturnsFalse(){
        //Act
        UUID id = new UUID(2, 2);
        balance.addTransaction(BigDecimal.TEN, TransactionType.CREDIT, id);
        //Assert
        assertThat(balance.transactionExists(new UUID(3, 2))).isEqualTo(false);
    }
    @Test
    public void whenReportRequestedThenReportReturned(){
        //Act
        balance.addTransaction(BigDecimal.TEN, TransactionType.CREDIT, new UUID(2, 2));
        StatisticalReport report = balance.getReport(OffsetDateTime.now().minusDays(1), OffsetDateTime.now().plusDays(1));
        //Assert
        assertThat(report.getAmountMax()).isEqualTo(BigDecimal.TEN);
        assertThat(report.getAmountMin()).isEqualTo(BigDecimal.TEN);
    }
    @Test
    public void whenDataRequestedThenDataInRightPeriodReturned(){
        //Act
        UUID id = new UUID(2, 2);
        balance.addTransaction(BigDecimal.TEN, TransactionType.CREDIT, id);
        Transaction transaction = balance.getData(OffsetDateTime.now().minusDays(1), OffsetDateTime.now().plusDays(1)).get(0);
        List<Transaction> transactionsBefore =
                balance.getData(OffsetDateTime.now().minusDays(2), OffsetDateTime.now().minusDays(1));
        //Assert
        assertThat(transaction.getAmount()).isEqualTo(BigDecimal.TEN);
        assertThat(transaction.getType()).isEqualTo(TransactionType.CREDIT);
        assertThat(transaction.getProcessId()).isEqualTo(id);
        assertThat(transactionsBefore.isEmpty()).isEqualTo(true);
    }
    @Test
    public void whenDataRequestedThenDataWithRightAmountReturned(){
        //Act
        balance.addTransaction(BigDecimal.TEN, TransactionType.CREDIT, new UUID(2, 2));
        balance.addTransaction(BigDecimal.ZERO, TransactionType.CREDIT, new UUID(2, 2));
        balance.addTransaction(BigDecimal.valueOf(5), TransactionType.CREDIT, new UUID(2, 2));
        List<Transaction> transactionsBetweenOneAndSix =
                balance.getData(OffsetDateTime.now().minusDays(2), OffsetDateTime.now().plusDays(1),
                        BigDecimal.ONE, BigDecimal.valueOf(6));
        List<Transaction> transactionsBetweenSixAndEleven =
                balance.getData(OffsetDateTime.now().minusDays(2), OffsetDateTime.now().plusDays(1),
                        BigDecimal.valueOf(6), BigDecimal.valueOf(11));
        List<Transaction> transactionsBetweenElevenAndTwelve =
                balance.getData(OffsetDateTime.now().minusDays(2), OffsetDateTime.now().plusDays(1),
                        BigDecimal.valueOf(11), BigDecimal.valueOf(12));
        List<Transaction> transactionsAll =
                balance.getData(OffsetDateTime.now().minusDays(2), OffsetDateTime.now().plusDays(1),
                        BigDecimal.valueOf(-1), BigDecimal.valueOf(12));

        //Assert
        assertThat(transactionsBetweenElevenAndTwelve.isEmpty()).isEqualTo(true);
        assertThat(transactionsBetweenSixAndEleven.size()).isEqualTo(1);
        assertThat(transactionsBetweenSixAndEleven.get(0).getAmount()).isEqualTo(BigDecimal.TEN);
        assertThat(transactionsBetweenOneAndSix.size()).isEqualTo(1);
        assertThat(transactionsBetweenOneAndSix.get(0).getAmount()).isEqualTo(BigDecimal.valueOf(5));
        assertThat(transactionsAll.size()).isEqualTo(3);
    }
    @Test
    public void whenDataRequestedThenDataOfRightTypeReturned(){
        //Act
        balance.addTransaction(BigDecimal.TEN, TransactionType.CREDIT, new UUID(2, 2));
        balance.addTransaction(BigDecimal.TEN, TransactionType.REFUND, new UUID(2, 2));
        balance.addTransaction(BigDecimal.TEN, TransactionType.CREDIT, new UUID(2, 2));
        List<Transaction> transactionsCredit =
                balance.getData(OffsetDateTime.now().minusDays(2), OffsetDateTime.now().plusDays(1),
                        BigDecimal.valueOf(6), BigDecimal.valueOf(11), TransactionType.CREDIT);
        List<Transaction> transactionsRefund =
                balance.getData(OffsetDateTime.now().minusDays(2), OffsetDateTime.now().plusDays(1),
                        BigDecimal.valueOf(6), BigDecimal.valueOf(11), TransactionType.REFUND);
        List<Transaction> transactionsWithdraw =
                balance.getData(OffsetDateTime.now().minusDays(2), OffsetDateTime.now().plusDays(1),
                        BigDecimal.valueOf(6), BigDecimal.valueOf(11), TransactionType.WITHDRAW);
        //Assert
        assertThat(transactionsCredit.size()).isEqualTo(2);
        assertThat(transactionsCredit.get(0).getType()).isEqualTo(TransactionType.CREDIT);
        assertThat(transactionsRefund.size()).isEqualTo(1);
        assertThat(transactionsRefund.get(0).getType()).isEqualTo(TransactionType.REFUND);
        assertThat(transactionsWithdraw.size()).isEqualTo(0);
    }
}
