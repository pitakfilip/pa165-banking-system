package cz.muni.pa165.banking.domain.balance.repository;

import cz.muni.pa165.banking.domain.balance.Balance;
import cz.muni.pa165.banking.domain.report.StatisticalReport;
import cz.muni.pa165.banking.domain.transaction.Transaction;
import cz.muni.pa165.banking.domain.transaction.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Martin Mojzis
 */
public interface BalancesRepository {

    Optional<Balance> findById(String id);
    void addBalance(String id);

    List<String> getAllIds();
}
