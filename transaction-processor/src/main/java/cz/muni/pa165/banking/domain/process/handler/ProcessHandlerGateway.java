package cz.muni.pa165.banking.domain.process.handler;

import cz.muni.pa165.banking.domain.money.CurrencyConverter;
import cz.muni.pa165.banking.domain.process.repository.ProcessRepository;
import cz.muni.pa165.banking.domain.process.repository.ProcessTransactionRepository;
import cz.muni.pa165.banking.domain.remote.AccountService;
import cz.muni.pa165.banking.domain.transaction.TransactionType;
import cz.muni.pa165.banking.exception.EntityNotFoundException;
import cz.muni.pa165.banking.exception.UnexpectedValueException;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.UUID;

public class ProcessHandlerGateway {

    public static void handle(UUID processUuid, TransactionType type, ProcessRepository processRepository, ProcessTransactionRepository processTransactionRepository, AccountService accountService, CurrencyConverter currencyConverter, TransactionTemplate transactionTemplate) {
        if (!processRepository.idExists(processUuid)) {
            throw new EntityNotFoundException(String.format("Process with uuid {%s} not found", processUuid));
        }

        if (type == null) {
            throw new UnexpectedValueException("Transaction type not defined");
        }
        
        ProcessHandler handler = switch (type) {
            case WITHDRAWAL -> new WithdrawHandler();
            case DEPOSIT -> new DepositHandler();
            case TRANSFER -> new TransferHandler();
            case SCHEDULED -> new ScheduledHandler();
            case REFUND -> new RefundHandler();
        };
        
        handler.handle(processUuid, processRepository, processTransactionRepository, accountService, currencyConverter, transactionTemplate);
    }
}
