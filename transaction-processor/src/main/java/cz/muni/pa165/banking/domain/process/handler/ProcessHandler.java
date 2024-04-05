package cz.muni.pa165.banking.domain.process.handler;

import cz.muni.pa165.banking.domain.money.CurrencyConverter;
import cz.muni.pa165.banking.domain.process.Process;
import cz.muni.pa165.banking.domain.process.ProcessOperations;
import cz.muni.pa165.banking.domain.process.ProcessTransaction;
import cz.muni.pa165.banking.domain.process.repository.ProcessRepository;
import cz.muni.pa165.banking.domain.process.repository.ProcessTransactionRepository;
import cz.muni.pa165.banking.domain.process.status.Status;
import cz.muni.pa165.banking.domain.process.status.StatusInformation;
import cz.muni.pa165.banking.domain.remote.AccountService;
import cz.muni.pa165.banking.exception.EntityNotFoundException;
import cz.muni.pa165.banking.exception.UnexpectedValueException;

import java.time.Instant;
import java.util.UUID;

abstract class ProcessHandler {

    abstract void evaluate(ProcessTransaction processTransaction, AccountService accountService, CurrencyConverter currencyConverter);


    final void handle(UUID processUuid,
                      ProcessRepository processRepository,
                      ProcessTransactionRepository processTransactionRepository,
                      AccountService accountService,
                      CurrencyConverter currencyConverter) {
        
        Process process = processRepository.findById(processUuid.toString());
        validateProcess(process);

        ProcessTransaction processTransaction = processTransactionRepository.findTransactionByProcessId(processUuid);
        if (processTransaction == null) {
            throw new EntityNotFoundException("Process is not linked to any transaction request");
        }

        Status newStatus = Status.PROCESSED;
        String message = "Deposit finalized";
        RuntimeException thrownException = null;
        try {
            evaluate(processTransaction, accountService, currencyConverter);
        } catch (RuntimeException e) {
            newStatus = Status.FAILED;
            message = e.getMessage();
            thrownException = e;
        }
        
        ProcessOperations.changeState(process, new StatusInformation(Instant.now(), newStatus, message));
        processRepository.save(process);

        if (thrownException != null) {
            throw thrownException;
        }
    }

    private void validateProcess(Process process) {
        if (process.getStatus().equals(Status.FAILED)) {
            throw new UnexpectedValueException(
                    "Process already closed, ended with failure",
                    "Failure information: " + process.getStatusInformation()
            );
        }
        if (process.getStatus().equals(Status.PROCESSED)) {
            throw new UnexpectedValueException("Process already finalized, ended successfully", process.getStatusInformation());
        }
    }

}
