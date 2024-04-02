package cz.muni.pa165.banking.application.mapper;

import cz.muni.pa165.banking.account.query.dto.Transaction;
import cz.muni.pa165.banking.domain.balance.Balance;
import cz.muni.pa165.banking.domain.balance.service.BalanceService;
import cz.muni.pa165.banking.domain.transaction.TransactionType;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

/**
 * @author Martin Mojzis
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface BalanceMapper {
    //TODO other types according to openapi
    TransactionType mapTypeOut(Transaction.TransactionTypeEnum type);

    Transaction.TransactionTypeEnum mapTypeIn(TransactionType type);

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
}
