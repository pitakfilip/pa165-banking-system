package cz.muni.pa165.banking.domain.scheduled;

import cz.muni.pa165.banking.domain.scheduled.recurrence.RecurrenceType;

import java.math.BigDecimal;

public record ScheduledPaymentProjection(String senderAccount, String receiverAccount, BigDecimal amount, RecurrenceType type, Integer day) {}
