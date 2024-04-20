package cz.muni.pa165.banking.application.service;

import cz.muni.pa165.banking.domain.account.Account;
import cz.muni.pa165.banking.domain.account.repository.AccountRepository;
import cz.muni.pa165.banking.domain.scheduled.ScheduledPayment;
import cz.muni.pa165.banking.domain.scheduled.ScheduledPaymentProjection;
import cz.muni.pa165.banking.domain.scheduled.recurrence.Recurrence;
import cz.muni.pa165.banking.domain.scheduled.recurrence.RecurrenceType;
import cz.muni.pa165.banking.domain.scheduled.repository.ScheduledPaymentRepository;
import cz.muni.pa165.banking.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AccountService {
    
    private final AccountRepository accountRepository;
    
    private final ScheduledPaymentRepository scheduledPaymentsRepository;

    public AccountService(AccountRepository accountRepository, ScheduledPaymentRepository scheduledPaymentsRepository){
        this.accountRepository = accountRepository;
        this.scheduledPaymentsRepository = scheduledPaymentsRepository;
    }
    
    public Account createAccount(Account newAccount){
        return accountRepository.save(newAccount);
    }

    public Account findById(Long accountId) throws EntityNotFoundException {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account with id: " + accountId + " was not found."));
    }
    
    public Account findByNumber(String accountNumber) throws EntityNotFoundException {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new EntityNotFoundException("Account with number: " + accountNumber + " was not found."));
    }

    public ScheduledPayment schedulePayment(ScheduledPayment newScheduledPayment) {
        return scheduledPaymentsRepository.save(newScheduledPayment);
    }

    @Transactional(rollbackFor = Exception.class)
    public ScheduledPayment createNewScheduledPayment(String sender, String receiver, BigDecimal amount, RecurrenceType recurrenceType, Integer day) {
        Account senderAccount = findByNumber(sender);
        Account receiverAccount = findByNumber(receiver);

        ScheduledPayment newScheduledPayment = new ScheduledPayment();
        newScheduledPayment.setSourceAccountId(senderAccount.getId());
        newScheduledPayment.setTargetAccountId(receiverAccount.getId());
        newScheduledPayment.setAmount(amount);

        Recurrence recurrence = new Recurrence();
        recurrence.setType(recurrenceType);
        recurrence.setPaymentDay(day);
        newScheduledPayment.setRecurrence(recurrence);

        return scheduledPaymentsRepository.save(newScheduledPayment);
    }

    @Transactional(readOnly = true)
    public List<ScheduledPaymentProjection> findScheduledPaymentsByAccount(String accountNumber) {
        Account senderAccount = findByNumber(accountNumber);
        List<ScheduledPayment> payments = scheduledPaymentsRepository.findBySourceAccountId(senderAccount.getId());

        Map<Long, String> targetMap = payments.stream()
                .collect(
                        Collectors.toMap(
                                ScheduledPayment::getTargetAccountId,
                                payment -> String.valueOf(accountRepository.findById(payment.getTargetAccountId())),
                                (existingVal, newVal) -> existingVal
                        )
                );

        return payments.stream()
                .map(payment -> new ScheduledPaymentProjection(
                                accountNumber,
                                targetMap.get(payment.getTargetAccountId()),
                                payment.getAmount(),
                                payment.getRecurrence().getType(),
                                payment.getRecurrence().getPaymentDay()
                        )
                )
                .toList();
    }

}
