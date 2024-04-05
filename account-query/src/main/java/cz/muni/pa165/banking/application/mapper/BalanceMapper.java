package cz.muni.pa165.banking.application.mapper;

import cz.muni.pa165.banking.account.query.dto.Transaction;
import cz.muni.pa165.banking.account.query.dto.TransactionStatistics;
import cz.muni.pa165.banking.account.query.dto.TransactionsReport;
import cz.muni.pa165.banking.domain.report.StatisticalReport;
import cz.muni.pa165.banking.domain.transaction.TransactionType;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.util.Date;

/**
 * @author Martin Mojzis
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BalanceMapper {
    TransactionType mapTypeOut(cz.muni.pa165.banking.account.query.dto.TransactionType type);
    cz.muni.pa165.banking.account.query.dto.TransactionType mapTypeIn(TransactionType type);
    default java.util.Date mapDateIn(java.time.@jakarta.validation.Valid OffsetDateTime value) {
        return new Date(value.toInstant().toEpochMilli());
    }

    default java.time.OffsetDateTime mapDate(java.util.Date date) {
        return date.toInstant().atOffset(ZoneOffset.UTC);
    }

    default cz.muni.pa165.banking.domain.transaction.Transaction mapTransactionOut(Transaction transaction) {
        return new
                cz.muni.pa165.banking.domain.transaction.Transaction(mapTypeOut(transaction.getTransactionType()),
                transaction.getAmount(), transaction.getDate(), transaction.getProcessId());
    }
    default Transaction mapTransactionIn(cz.muni.pa165.banking.domain.transaction.Transaction transaction){
        Transaction result = new Transaction();
        result.setAmount(transaction.getAmount());
        result.setDate(transaction.getDate());
        result.setProcessId(transaction.getProcessId());
        result.setTransactionType(mapTypeIn(transaction.getType()));
        return result;
    }
    default TransactionStatistics mapStatisticsOut(cz.muni.pa165.banking.domain.report.TransactionStatistics statistics){
        TransactionStatistics result = new TransactionStatistics();
        result.setTransactionType(mapTypeIn(statistics.getType()));
        result.setAmountOut(statistics.getAmountOut());
        result.setTimesOut(BigDecimal.valueOf(statistics.getTimesOut()));
        result.setAmountIn(statistics.getAmountIn());
        result.setTimesIn(BigDecimal.valueOf(statistics.getTimesIn()));
        if(statistics.getTimesOut() != 0) {
            result.setAvgOut(statistics.getAmountOut().divide(new BigDecimal(statistics.getTimesOut())));
        }
        else{
            result.setAvgOut(BigDecimal.ZERO);
        }
        if(statistics.getTimesIn() != 0) {
            result.setAvgIn(statistics.getAmountIn().divide(new BigDecimal(statistics.getTimesIn())));
        }
        else{
            result.setAvgIn(BigDecimal.ZERO);
        }
        return result;
    }
    default TransactionsReport mapReportOut(StatisticalReport report){
        TransactionsReport result = new TransactionsReport();
        result.setCreditAmount(mapStatisticsOut(report.getCreditAmount()));
        result.setDepositAmount(mapStatisticsOut(report.getDepositAmount()));
        result.setTotalAmount(mapStatisticsOut(report.getTotalAmount()));
        result.setWithdrawalAmount(mapStatisticsOut(report.getWithdrawalAmount()));
        result.setCrossAccountAmount(mapStatisticsOut(report.getCrossAccountAmount()));
        return result;
    }
}
