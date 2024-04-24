package cz.muni.pa165.banking.application.mapper;


import cz.muni.pa165.banking.domain.account.Account;
import cz.muni.pa165.banking.domain.money.Money;
import cz.muni.pa165.banking.domain.process.Process;
import cz.muni.pa165.banking.domain.process.status.Status;
import cz.muni.pa165.banking.domain.transaction.Transaction;
import cz.muni.pa165.banking.domain.transaction.TransactionType;
import cz.muni.pa165.banking.transaction.processor.dto.*;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface DtoMapper {
    
    // Input DTOs
    Money map(MoneyDto source);

    Account map(AccountDto source);
    
    TransactionType map(TransactionTypeDto source);
    
    Transaction map(TransactionDto source);

    // Output DTOs
    StatusDto map(Status source);

    AccountDto map(Account source);
    
    TransactionTypeDto map(TransactionType source);

    MoneyDto map(Money source);
    
    default ProcessDto map(Process source) {
        ProcessDto dto = new ProcessDto();
        
        dto.setIdentifier(source.getUuid());
        dto.setStatus(map(source.getStatus()));
        dto.setInfo(source.getInformation());
        
        return dto;
    }
    
    default ProcessDetailDto map(Process process, Transaction transaction) {
        ProcessDetailDto dto = new ProcessDetailDto();
        dto.identifier(process.getUuid());
        dto.status(map(process.getStatus()));
        dto.info(process.getInformation());
        dto.source(map(transaction.getSource()));
        
        if (transaction.getTarget() != null) {
            dto.target(map(transaction.getTarget()));
        }
        
        dto.type(map(transaction.getType()));
        dto.amount(map(transaction.getMoney()));
        dto.detail(transaction.getDetail());
        
        return dto;
    }
}
