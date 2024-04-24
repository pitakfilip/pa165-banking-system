package cz.muni.pa165.banking.domain.messaging;

import cz.muni.pa165.banking.domain.transaction.TransactionType;

import java.util.Objects;
import java.util.UUID;

public record ProcessRequest(UUID uuid, TransactionType type) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProcessRequest request = (ProcessRequest) o;
        return Objects.equals(uuid, request.uuid) && type == request.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, type);
    }

    @Override
    public String toString() {
        return "ProcessRequest{" +
                "uuid=" + uuid +
                ", type=" + type +
                '}';
    }
    
}
