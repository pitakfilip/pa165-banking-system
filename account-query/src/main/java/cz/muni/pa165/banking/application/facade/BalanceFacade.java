package cz.muni.pa165.banking.application.facade;

import cz.muni.pa165.banking.account.query.dto.Transaction;
import cz.muni.pa165.banking.account.query.dto.TransactionType;
import cz.muni.pa165.banking.account.query.dto.TransactionsReport;
import cz.muni.pa165.banking.application.exception.NotFoundAccountException;
import cz.muni.pa165.banking.application.mapper.BalanceMapper;
import cz.muni.pa165.banking.domain.balance.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

/**
 * @author Martin Mojzis
 */
@Service
public class BalanceFacade {

    private final BalanceService balanceService;
    private final BalanceMapper balanceMapper;

    public BalanceFacade(BalanceService balanceService, BalanceMapper balanceMapper) {
        this.balanceService = balanceService;
        this.balanceMapper = balanceMapper;
    }

    public void createNewBalance(String id) throws NotFoundAccountException {
        balanceService.addNewBalance(id);
    }

    public void addToBalance(String id, UUID processId, BigDecimal value, TransactionType type) {
        balanceService.addToBalance(id, value, processId, balanceMapper.mapTypeOut(type));
    }

    public BigDecimal getBalance(String id) throws NotFoundAccountException {
        return balanceService.getBalance(id);
    }

    public List<Transaction> getTransactions(String id, LocalDate beginning, LocalDate end, BigDecimal minAmount,
                                             BigDecimal maxAmount, TransactionType type) {
        List<cz.muni.pa165.banking.domain.transaction.Transaction> toReturn;
        toReturn = balanceService.getTransactions(id, OffsetDateTime.of(beginning, LocalTime.MIDNIGHT, ZoneOffset.UTC), OffsetDateTime.of(end, LocalTime.MIDNIGHT, ZoneOffset.UTC),
                minAmount, maxAmount, balanceMapper.mapTypeOut(type));
        return toReturn.stream().map(balanceMapper::mapTransactionIn).toList();
    }

    public TransactionsReport getReport(String id, LocalDate beginning, LocalDate end) {
        return balanceMapper.mapReportOut(balanceService.getReport(id,
                OffsetDateTime.of(beginning, LocalTime.MIDNIGHT, ZoneOffset.UTC),
                OffsetDateTime.of(end, LocalTime.MIDNIGHT, ZoneOffset.UTC)));
    }

    public List<Transaction> getAllTransactions(LocalDate beginning, LocalDate end, BigDecimal minAmount,
                                                BigDecimal maxAmount, TransactionType type) {
        List<cz.muni.pa165.banking.domain.transaction.Transaction> toReturn =
                balanceService.getAllTransactions(OffsetDateTime.of(beginning, LocalTime.MIDNIGHT, ZoneOffset.UTC), OffsetDateTime.of(end, LocalTime.MIDNIGHT, ZoneOffset.UTC), minAmount, maxAmount,
                        balanceMapper.mapTypeOut(type));
        return toReturn.stream().map(balanceMapper::mapTransactionIn).toList();
    }

    public void deleteBalance(String id) throws NotFoundAccountException {
        balanceService.deleteBalance(id);
    }
}
