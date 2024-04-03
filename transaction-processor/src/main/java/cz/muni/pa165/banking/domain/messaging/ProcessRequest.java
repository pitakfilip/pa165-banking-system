package cz.muni.pa165.banking.domain.messaging;

import cz.muni.pa165.banking.domain.transaction.TransactionType;

import java.util.UUID;

public record ProcessRequest(UUID uuid, TransactionType type) {
}
