package cz.muni.pa165.banking.application.service;

import cz.muni.pa165.banking.account.management.AccountApi;
import cz.muni.pa165.banking.account.management.dto.ScheduledPaymentDto;
import cz.muni.pa165.banking.account.management.dto.ScheduledPaymentsDto;
import cz.muni.pa165.banking.domain.account.Account;
import cz.muni.pa165.banking.domain.messaging.MessageProducer;
import cz.muni.pa165.banking.domain.money.Money;
import cz.muni.pa165.banking.domain.process.Process;
import cz.muni.pa165.banking.domain.process.ProcessFactory;
import cz.muni.pa165.banking.domain.process.repository.ProcessRepository;
import cz.muni.pa165.banking.domain.process.repository.ProcessTransactionRepository;
import cz.muni.pa165.banking.domain.transaction.Transaction;
import cz.muni.pa165.banking.domain.transaction.TransactionType;
import cz.muni.pa165.banking.exception.ServerError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Currency;
import java.util.Objects;

@Service
public class ScheduledPaymentService {

    private final Logger LOGGER = LoggerFactory.getLogger(ScheduledPaymentService.class);

    private final AccountApi accountApi;

    private final ProcessTransactionRepository processTransactionRepository;

    private final ProcessRepository processRepository;

    private final MessageProducer messageProducer;

    ScheduledPaymentService(AccountApi accountApi,
                            ProcessTransactionRepository processTransactionRepository,
                            ProcessRepository processRepository,
                            MessageProducer messageProducer) {
        this.accountApi = accountApi;
        this.processTransactionRepository = processTransactionRepository;
        this.processRepository = processRepository;
        this.messageProducer = messageProducer;
    }

    @Scheduled(cron = "${scheduled-payments.cron.expression}")
    public void executeScheduledPayments() {
        LocalDate now = LocalDate.now();
        ResponseEntity<ScheduledPaymentsDto> response = accountApi.getScheduledPaymentsOf(now);
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new ServerError("Call to Account Management service unsuccessful.");
        }

        ProcessFactory factory = new ProcessFactory(processTransactionRepository, processRepository);
        ScheduledPaymentsDto payments = Objects.requireNonNull(response.getBody());
        for (ScheduledPaymentDto payment : payments.getScheduledPayments()) {
            Transaction newTransaction = transactionForScheduledPayment(payment);
            Process process = factory.create(newTransaction, messageProducer);
            LOGGER.info("[Create Scheduled Payment Process] %s" + process.getUuid());
        }

        LOGGER.info("Finished creating processes for scheduled payments.");
    }

    private Transaction transactionForScheduledPayment(ScheduledPaymentDto payment) {
        Account source = new Account(payment.getSenderAccount());
        Account target = new Account(payment.getReceiverAccount());
        Money money = new Money(payment.getAmount(), Currency.getInstance(payment.getCurrencyCode()));
        return new Transaction(
                source,
                target,
                TransactionType.SCHEDULED,
                money,
                "Automatic execution of scheduled payment"
        );
    }

}
