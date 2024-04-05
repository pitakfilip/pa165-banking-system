package cz.muni.pa165.banking.domain.process.status;

import java.time.Instant;

public record StatusInformation(Instant when, Status status, String information) {
}
