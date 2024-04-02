package cz.muni.pa165.banking.application.mapper;

import cz.muni.pa165.banking.account.query.dto.Transaction;
import cz.muni.pa165.banking.domain.balance.Balance;
import cz.muni.pa165.banking.domain.balance.service.BalanceService;
import cz.muni.pa165.banking.domain.transaction.TransactionType;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.time.OffsetDateTime;
import java.util.Date;

/**
 * @author Martin Mojzis
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface BalanceMapper {
    //TODO other types according to openapi
    TransactionType typeInToTypeOut(Transaction.TransactionTypeEnum type);


    Transaction.TransactionTypeEnum typeOutToTypeIn(TransactionType type);
    default java.util.Date mapDateIn(java.time.@jakarta.validation.Valid OffsetDateTime value){
        return new Date(value.toInstant().toEpochMilli());
    }

}
