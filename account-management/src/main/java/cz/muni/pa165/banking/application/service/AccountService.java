package cz.muni.pa165.banking.application.service;

import cz.muni.pa165.banking.account.query.SystemServiceApi;
import cz.muni.pa165.banking.domain.account.Account;
import cz.muni.pa165.banking.domain.account.repository.AccountRepository;
import cz.muni.pa165.banking.domain.scheduled.ScheduledPayment;
import cz.muni.pa165.banking.domain.scheduled.ScheduledPaymentProjection;
import cz.muni.pa165.banking.domain.scheduled.recurrence.Recurrence;
import cz.muni.pa165.banking.domain.scheduled.recurrence.RecurrenceQuerySpecificationBuilder;
import cz.muni.pa165.banking.domain.scheduled.recurrence.RecurrenceType;
import cz.muni.pa165.banking.domain.scheduled.repository.ScheduledPaymentRepository;
import cz.muni.pa165.banking.domain.user.repository.UserRepository;
import cz.muni.pa165.banking.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private final SystemServiceApi balanceApi;

    private final AccountRepository accountRepository;

    private final UserRepository userRepository;

    private final ScheduledPaymentRepository scheduledPaymentsRepository;

    public AccountService(SystemServiceApi balanceApi, AccountRepository accountRepository, UserRepository userRepository, ScheduledPaymentRepository scheduledPaymentsRepository){
        this.balanceApi = balanceApi;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.scheduledPaymentsRepository = scheduledPaymentsRepository;
    }

    public Account createAccount(Account newAccount) {
        if (!userRepository.existsById(newAccount.getUserId())) {
            throw new EntityNotFoundException("User with id: " + newAccount.getUserId() + " was not found.");
        }
        balanceApi.createBalance(newAccount.getAccountNumber());
        return accountRepository.save(newAccount);
    }

    public Account findById(Long accountId){
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account with id: " + accountId + " was not found."));
    }
    
    public Account findByNumber(String accountNumber){
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new EntityNotFoundException("Account with number: " + accountNumber + " was not found."));
    }

    public ScheduledPayment schedulePayment(ScheduledPayment newScheduledPayment) {
        if (!accountRepository.existsById(newScheduledPayment.getSourceAccountId())){
            throw new EntityNotFoundException("Account with id: " + newScheduledPayment.getSourceAccountId() + " was not found." +" id1: "+ newScheduledPayment.getId());
        }
        if (!accountRepository.existsById(newScheduledPayment.getTargetAccountId())){
            throw new EntityNotFoundException("Account with id: " + newScheduledPayment.getTargetAccountId() + " was not found." +" id2: "+ newScheduledPayment.getId());
        }
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
        newScheduledPayment.setCurrencyCode(senderAccount.getCurrency().getCurrencyCode());

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

    public List<ScheduledPayment> scheduledPaymentsOfDay(LocalDate date) {
        return scheduledPaymentsRepository.findAll(RecurrenceQuerySpecificationBuilder.forDay(date));
    }
}
